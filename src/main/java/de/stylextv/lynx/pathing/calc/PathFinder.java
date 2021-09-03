package de.stylextv.lynx.pathing.calc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.util.world.Offset;
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
		for(Offset o : Offset.BLOCK_NEIGHBOURS) {
			
			int ox = o.getBlockX();
			int oy = o.getBlockY();
			int oz = o.getBlockZ();
			
			int x = node.getX() + ox;
			int y = node.getY() + oy;
			int z = node.getZ() + oz;
			
			if(oy == -1) {
				
				BlockType type = getMapNode(x, y, z).getType();
				
				while(y > 0) {
					
					if(type == BlockType.WATER) break;
					
					Node n = getMapNode(x, y - 1, z);
					
					type = n.getType();
					
					if(!type.isPassable()) break;
					
					y--;
				}
			}
			
			if(y > 0) {
				addAdjacentNode(node, x, y, z);
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
	
	// TODO outsource this method
	private boolean isValidNode(Node node, Node parent) {
		int x = node.getX();
		int y = node.getY();
		int z = node.getZ();
		
		int disX = Math.abs(parent.getX() - x);
		int disY = Math.abs(parent.getY() - y);
		int disZ = Math.abs(parent.getZ() - z);
		
		int dis = disX + disY + disZ;
		
		boolean downwards = node.getY() < parent.getY();
		
		boolean needsSupport = dis != 1 || downwards;
		
		if(!canStandAt(node, needsSupport)) return false;
		
		return true;
	}
	
	private boolean canStandAt(Node n, boolean needsSupport) {
		if(n.getType() == BlockType.WATER) return true;
		
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		Node below = getMapNode(x, y - 1, z);
		
		return !needsSupport || below.getType() == BlockType.SOLID;
	}
	
	private Node getMapNode(int x, int y, int z) {
		long hash = Node.posAsLong(x, y, z);
		
		Node n = map.get(hash);
		
		if(n != null) return n;
		
		n = new Node(x, y, z);
		
		n.updateHeuristic(goal);
		
		map.put(hash, n);
		
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
