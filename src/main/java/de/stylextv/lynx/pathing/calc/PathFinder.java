package de.stylextv.lynx.pathing.calc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.pathing.calc.cost.Cost;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;

public class PathFinder {
	
	private Long2ObjectOpenHashMap<Node> map;
	
	private PriorityQueue<Node> openList;
	
	private Set<Node> closedSet;
	
	private Goal goal;
	
	public PathFinder(Goal goal) {
		this.goal = goal;
		
		this.map = new Long2ObjectOpenHashMap<>(1024, 0.75f);
		
		this.openList = new PriorityQueue<Node>((Node n1, Node n2) -> Integer.compare(n1.getFinalCost(), n2.getFinalCost()));
		
		this.closedSet = new HashSet<>();
	}
	
	public Path find(BlockPos start) {
		return find(start.getX(), start.getY(), start.getZ());
	}
	
	public Path find(int startX, int startY, int startZ) {
		openList.add(getMapNode(startX, startY, startZ));
		
		while(!openList.isEmpty()) {
			Node n = openList.poll();
			
			closedSet.add(n);
			
			if(goal.isFinalNode(n)) {
				
				List<Node> nodes = backtrace(n);
				
				return new Path(nodes);
				
			} else {
				addAdjacentNodes(n);
			}
		}
		
		return null;
	}
	
	private List<Node> backtrace(Node n) {
		List<Node> list = new ArrayList<Node>();
		
		while(n != null) {
			list.add(0, n);
			
			n = n.getParent();
		}
		
		return list;
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
						
						if(ry >= 0 && ry < 255) {
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
			int cost = parent.costToNode(n);
			
			if(cost >= Cost.INFINITY) return;
			
			if(openList.contains(n)) {
				
				if(n.updateParent(parent, cost)) {
					openList.remove(n);
					openList.add(n);
				}
				
			} else {
				
				n.setParent(parent, cost);
				
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
		
		boolean diagonally = disX + disZ + disY > 1;
		
		if(diagonally && !canStandAt(node)) return false;
		
		Node above = getMapNode(x, y + 1, z);
		
		if(above.getType() == BlockType.WATER) return false;
		
		if(isDangerousNode(node) || isDangerousNode(above)) return false;
		
		return true;
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
		
		return n;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
}
