package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.cost.Cost;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import net.minecraft.core.BlockPos;

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
		type = CacheManager.getBlockType(x, y, z);
	}
	
	public void updateHeuristic(Goal goal) {
		hCost = goal.heuristic(this);
	}
	
	public int getFinalCost() {
		return gCost + hCost;
	}
	
	public int costToNode(Node n) {
		return Cost.getCost(n, this);
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
	
	public BlockPos blockPos() {
		return new BlockPos(x, y, z);
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
	
	@Override
	public String toString() {
		return String.format("Node{x=%s, y=%s, z=%s, type=%s}", x, y, z, type);
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
