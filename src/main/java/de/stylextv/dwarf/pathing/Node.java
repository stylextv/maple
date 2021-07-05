package de.stylextv.dwarf.pathing;

import de.stylextv.dwarf.cache.BlockType;
import de.stylextv.dwarf.cache.WorldCache;
import de.stylextv.dwarf.pathing.goal.IGoal;
import net.minecraft.util.math.BlockPos;

public class Node {
	
	public static final int COST_PER_UNIT = 10;
	
	private static final int[] MOVE_COSTS = new int[] {
			10, 14, 17
	};
	
	private static final int WATER_COST = 3;
	
	private int x;
	private int y;
	private int z;
	
	private BlockType type;
	
	private Node parent;
	
	private int gCost;
	private int hCost;
	
	public Node(BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public Node(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		updateBlockType();
	}
	
	public void updateBlockType() {
		type = WorldCache.getBlockType(x, y, z);
	}
	
	public void calcHeuristic(IGoal goal) {
		hCost = goal.calcHeuristic(this);
	}
	
	public int getFinalCost() {
		return gCost + hCost;
	}
	
	public int costToNode(Node n) {
		int i1 = Math.abs(n.getX() - x);
		int i2 = Math.abs(n.getY() - y);
		int i3 = Math.abs(n.getZ() - z);
		
		int i = i1 + i2 + i3;
		
		int cost = MOVE_COSTS[i - 1];
		
		if(n.getType() == BlockType.WATER) {
			cost *= WATER_COST;
		}
		
		return cost;
	}
	
	public boolean updateParent(Node n, int cost) {
		int i = n.getGCost() + cost;
		
		if(i < gCost) {
			setParent(n, cost);
			
			return true;
		}
		
		return false;
	}
	
	public void setParent(Node n, int cost) {
		parent = n;
		
		gCost = n.getGCost() + cost;
	}
	
	public long getHash() {
		return posAsLong(x, y, z);
	}
	
	public boolean equals(BlockPos pos) {
		return equals(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public boolean equals(Node n) {
		return equals(n.getX(), n.getY(), n.getZ());
	}
	
	public boolean equals(int x, int y, int z) {
		return this.x == x && this.y == y && this.z == z;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public BlockType getType() {
		return type;
	}
	
	public int getGCost() {
		return gCost;
	}
	
	public int getHCost() {
		return hCost;
	}
	
	public static long posAsLong(int x, int y, int z) {
		return BlockPos.asLong(x, y, z);
	}
	
}
