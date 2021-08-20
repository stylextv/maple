package de.stylextv.lynx.pathing.calc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.Movement;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;

public class PathFinder {
	
	private static final int MAX_CHUNK_BORDER_HITS = 50;
	
	private static final float[] PS_COEFFICIENTS = {1.5f, 2, 2.5f, 3, 4, 5, 10};
	
	private static final int PS_MIN_DISTANCE = 25;
	
	private Long2ObjectOpenHashMap<Node> map;
	
	private PriorityQueue<Node> openList;
	
	private Set<Node> closedSet;
	
	private Node[] partialSolutions;
	
	private Goal goal;
	
	private int chunkBorderHits;
	
	private boolean stop;
	
	private boolean pause;
	
	public PathFinder(Goal goal) {
		this.goal = goal;
		
		this.map = new Long2ObjectOpenHashMap<>(1024, 0.75f);
		
		this.openList = new PriorityQueue<Node>((Node n1, Node n2) -> Double.compare(n1.getFinalCost(), n2.getFinalCost()));
		
		this.closedSet = new HashSet<>();
		
		this.partialSolutions = new Node[PS_COEFFICIENTS.length];
	}
	
	public PathSegment find(BlockPos start, long time) {
		return find(start.getX(), start.getY(), start.getZ(), time);
	}
	
	public PathSegment find(int startX, int startY, int startZ, long time) {
		long startTime = System.currentTimeMillis();
		
		Node startNode = getMapNode(startX, startY, startZ);
		
		openList.add(startNode);
		
		while(!openList.isEmpty() && !stop) {
			Node n = openList.poll();
			
			closedSet.add(n);
			
			updatePartialSolutions(n);
			
			if(goal.isFinalNode(n)) {
				
				return backtrace(n);
				
			} else {
				addAdjacentNodes(n);
			}
			
			if(System.currentTimeMillis() - startTime > time || chunkBorderHits > MAX_CHUNK_BORDER_HITS) {
				
				pause = true;
				
				break;
			}
		}
		
		if(stop) return null;
		
		for(Node n : partialSolutions) {
			
			int dis = startNode.distanceSqr(n);
			
			boolean b = dis > PS_MIN_DISTANCE;
			
			if(b) return backtrace(n);
		}
		
		return null;
	}
	
	public void stop() {
		stop = true;
	}
	
	private void updatePartialSolutions(Node n) {
		for(int i = 0; i < PS_COEFFICIENTS.length; i++) {
			
			Node closest = partialSolutions[i];
			
			if(closest == null) {
				partialSolutions[i] = n;
				
				continue;
			}
			
			float f = PS_COEFFICIENTS[i];
			
			boolean closer = n.getPartialCost(f) < closest.getPartialCost(f);
			
			if(closer) partialSolutions[i] = n;
		}
	}
	
	private PathSegment backtrace(Node n) {
		List<Movement> list = new ArrayList<Movement>();
		
		while(true) {
			Node parent = n.getParent();
			
			if(parent == null) break;
			
			list.add(0, n.getMovement());
			
			n = parent;
		}
		
		if(list.isEmpty()) return null;
		
		return new PathSegment(list);
	}
	
	private void addAdjacentNodes(Node node) {
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				for(int z = -1; z <= 1; z++) {
					
					int disX = Math.abs(x);
					int disY = Math.abs(y);
					int disZ = Math.abs(z);
					
					int dis = disX + disY + disZ;
					
					if(dis != 0) {
						
						int rx = node.getX() + x;
						int ry = node.getY() + y;
						int rz = node.getZ() + z;
						
						if(y == -1) {
							
							BlockType type = getMapNode(rx, ry, rz).getType();
							
							if(type == BlockType.AIR) {
								while(ry > 0) {
									
									Node n = getMapNode(rx, ry - 1, rz);
									
									if(n.getType() != BlockType.AIR) break;
									
									ry--;
								}
							}
						}
						
						if(ry > 0) {
							addAdjacentNode(node, rx, ry, rz);
						}
					}
				}
			}
		}
	}
	
	private void addAdjacentNode(Node parent, int x, int y, int z) {
		Node n = getMapNode(x, y, z);
		
		if(closedSet.contains(n)) return;
		
		if(isValidNode(n, parent)) {
			Movement m = Movement.fromNodes(parent, n);
			
			double cost = m.cost();
			
			if(cost >= Cost.INFINITY) return;
			
			if(openList.contains(n)) {
				
				if(n.updateParent(parent, m, cost)) {
					openList.remove(n);
					openList.add(n);
				}
				
			} else {
				
				n.setParent(parent, m, cost);
				
				openList.add(n);
			}
		}
	}
	
	private boolean isValidNode(Node node, Node parent) {
		int x = node.getX();
		int y = node.getY();
		int z = node.getZ();
		
		int disX = Math.abs(parent.getX() - x);
		int disY = Math.abs(parent.getY() - y);
		int disZ = Math.abs(parent.getZ() - z);
		
		if(disY > 3) return false;
		
		boolean diagonally = disX + disZ + disY > 1;
		
		if(diagonally) {
			
			if(!canStandAt(node) || isBlocked(node, 2)) return false;
			
			Node higher = node;
			
			if(parent.getY() > higher.getY()) higher = parent;
			
			boolean b1 = isBlocked(node.getX(), higher.getY(), parent.getZ(), 2);
			boolean b2 = isBlocked(parent.getX(), higher.getY(), node.getZ(), 2);
			
			if(b1 && b2) return false;
		}
		
		Node above = getMapNode(x, y + 1, z);
		
		if(above.getType() == BlockType.WATER) return false;
		
		if(isDangerousNode(node) || isDangerousNode(above)) return false;
		
		return true;
	}
	
	private boolean isBlocked(Node n, int height) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return isBlocked(x, y, z, height);
	}
	
	private boolean isBlocked(int x, int y, int z, int height) {
		for(int i = 0; i < height; i++) {
			
			if(isBlocked(x, y + i, z)) return true;
		}
		
		return false;
	}
	
	private boolean isBlocked(int x, int y, int z) {
		Node n = getMapNode(x, y, z);
		
		return !n.getType().isPassable();
	}
	
	private boolean isDangerousNode(Node node) {
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				for(int z = -1; z <= 1; z++) {
					
					int disX = Math.abs(x);
					int disY = Math.abs(y);
					int disZ = Math.abs(z);
					
					int dis = disX + disY + disZ;
					
					if(dis <= 1) {
						
						int rx = node.getX() + x;
						int ry = node.getY() + y;
						int rz = node.getZ() + z;
						
						Node n = getMapNode(rx, ry, rz);
						
						if(n.getType() == BlockType.DANGER) return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean canStandAt(Node n) {
		if(n.getType() == BlockType.WATER) return true;
		
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		Node below = getMapNode(x, y - 1, z);
		
		return below.getType() == BlockType.SOLID;
	}
	
	private Node getMapNode(int x, int y, int z) {
		long hash = Node.posAsLong(x, y, z);
		
		Node n = map.get(hash);
		
		if(n != null) return n;
		
		n = new Node(x, y, z);
		
		n.updateHeuristic(goal);
		
		map.put(n.getHash(), n);
		
		if(n.getType().equals(BlockType.UNLOADED)) chunkBorderHits++;
		
		return n;
	}
	
	public Node getCurrentNode() {
		return openList.peek();
	}
	
	public Goal getGoal() {
		return goal;
	}
	
	public boolean wasStopped() {
		return stop;
	}
	
	public boolean wasPaused() {
		return pause;
	}
	
}
