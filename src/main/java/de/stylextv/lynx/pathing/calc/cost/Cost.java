package de.stylextv.lynx.pathing.calc.cost;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.Node;

public class Cost {
	
	public static final int INFINITY = 100000000;
	
	public static final int COST_PER_UNIT = 10;
	
	private static final int[] MOVEMENT_COSTS = new int[] {
			10, 14, 17
	};
	
	private static final int WATER_MULTIPLIER = 3;
	
	private static final int BUMP_MULTIPLIER = 2;
	
	// TODO factor in fall time cost
	
	public static int getCost(Node node, Node parent) {
		int x = node.getX();
		int y = node.getY();
		int z = node.getZ();
		
		int disX = Math.abs(parent.getX() - x);
		int disY = Math.abs(parent.getY() - y);
		int disZ = Math.abs(parent.getZ() - z);
		
		int dis = disX + disZ + Math.min(disY, 1);
		
		int cost = Cost.MOVEMENT_COSTS[dis - 1];
		
		if(node.getType() == BlockType.WATER) {
			cost *= Cost.WATER_MULTIPLIER;
		}
		
		boolean diagonally = disX + disZ == 2;
		
		Node higherNode = node;
		Node lowerNode = parent;
		
		if(y != parent.getY()) {
			
			if(higherNode.getY() < lowerNode.getY()) {
				
				higherNode = parent;
				lowerNode = node;
			}
			
			int bonkX = lowerNode.getX();
			int bonkY = higherNode.getY() + 1;
			int bonkZ = lowerNode.getZ();
			
			int costToBreak = BreakingCost.getCost(bonkX, bonkY, bonkZ);
			
			if(costToBreak != 0 && diagonally) return Cost.INFINITY;
			
			cost += costToBreak;
		}
		
		if(diagonally) {
			
			boolean b1 = isBlocked(x, higherNode.getY(), parent.getZ(), 2);
			boolean b2 = isBlocked(parent.getX(), higherNode.getY(), z, 2);
			
			if(b1 && b2) return Cost.INFINITY;
			if(b1 || b2) cost *= Cost.BUMP_MULTIPLIER;
		}
		
		for(int i = 0; i < 2; i++) {
			
			int costToBreak = BreakingCost.getCost(x, y + i, z);
			
			if(costToBreak != 0 && diagonally) return Cost.INFINITY;
			
			cost += costToBreak;
		}
		
		boolean pillar = dis == 1 && y == parent.getY() + 1;
		
		if(pillar || !canStandAt(node)) {
			
			int costToPlace = PlacingCost.getCost(x, y - 1, z);
			
			cost += costToPlace;
		}
		
		return cost;
	}
	
	private static boolean isBlocked(int x, int y, int z, int height) {
		for(int i = 0; i < height; i++) {
			if(isBlocked(x, y + i, z)) return true;
		}
		
		return false;
	}
	
	private static boolean isBlocked(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		return !type.isPassable();
	}
	
	private static boolean canStandAt(Node n) {
		if(n.getType() == BlockType.WATER) return true;
		
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		BlockType type = CacheManager.getBlockType(x, y - 1, z);
		
		return type == BlockType.SOLID;
	}
	
}
