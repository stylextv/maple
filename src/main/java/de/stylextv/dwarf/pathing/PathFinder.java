package de.stylextv.dwarf.pathing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.stylextv.dwarf.cache.BlockType;
import de.stylextv.dwarf.pathing.goal.IGoal;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;

public class PathFinder {
	
	private Long2ObjectOpenHashMap<Node> map;
	
	private PriorityQueue<Node> openList;
	
	private Set<Node> closedSet;
	
	private IGoal goal;
	
	public PathFinder(IGoal goal) {
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
	
	private void addAdjacentNodes(Node n) {
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				for(int z = -1; z <= 1; z++) {
					
					int disX = Math.abs(x);
					int disY = Math.abs(y);
					int disZ = Math.abs(z);
					
					int dis = disX + disY + disZ;
					
					if(dis != 0 && (disY == 0 || disX != 0 || disZ != 0)) {
						
						int rx = n.getX() + x;
						int ry = n.getY() + y;
						int rz = n.getZ() + z;
						
						if(ry >= 0 && ry < 255) {
							addAdjacentNode(n, rx, ry, rz);
						}
					}
				}
			}
		}
	}
	
	private void addAdjacentNode(Node node, int x, int y, int z) {
		Node n = getMapNode(x, y, z);
		
		if(closedSet.contains(n)) return;
		
		Node below = getMapNode(x, y - 1, z);
		
		if(n.getType() == BlockType.AIR && below.getType() == BlockType.SOLID) {
			int cost = node.costToNode(n);
			
			if(openList.contains(n)) {
				
				if(n.updateParent(node, cost)) {
					openList.remove(n);
					openList.add(n);
				}
				
			} else {
				
				n.setParent(node, cost);
				
				openList.add(n);
			}
		}
	}
	
	private Node getMapNode(int x, int y, int z) {
		long hash = Node.posAsLong(x, y, z);
		
		Node n = map.get(hash);
		
		if(n != null) return n;
		
		n = new Node(x, y, z);
		
		n.calcHeuristic(goal);
		
		map.put(n.getHash(), n);
		
		return n;
	}
	
	public IGoal getGoal() {
		return goal;
	}
	
}
