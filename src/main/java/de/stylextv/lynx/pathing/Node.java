package de.stylextv.lynx.pathing;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.WorldCache;
import de.stylextv.lynx.pathing.goal.Goal;
import net.minecraft.util.math.BlockPos;

public class Node {
	
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
	
	public void updateHeuristic(Goal goal) {
		hCost = goal.heuristic(this);
	}
	
	public int getFinalCost() {
		return gCost + hCost;
	}
	
	public int costToNode(Node n) {
		int i1 = Math.abs(n.getX() - x);
		int i2 = Math.abs(n.getY() - y);
		int i3 = Math.abs(n.getZ() - z);
		
		if(i2 > 1) i2 = 1;
		
		int i = i1 + i2 + i3;
		
		int cost = Cost.MOVEMENT_COSTS[i - 1];
		
		if(n.getType() == BlockType.WATER) {
			cost *= Cost.WATER_MULTIPLIER;
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
