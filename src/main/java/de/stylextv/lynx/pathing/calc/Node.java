package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.Movement;
import net.minecraft.core.BlockPos;

public class Node {
	
	private int x;
	private int y;
	private int z;
	
	private BlockType type;
	
	private Node parent;
	
	private Movement movement;
	
	private double gCost;
	private double hCost;
	
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
	
	public double getFinalCost() {
		return gCost + hCost;
	}
	
	public double getPartialCost(float coefficient) {
		return hCost + gCost / coefficient;
	}
	
	public boolean updateParent(Node n, Movement m, double cost) {
		double d = n.getGCost() + cost;
		
		if(d < gCost) {
			setParent(n, m, cost);
			
			return true;
		}
		
		return false;
	}
	
	public void setParent(Node n, Movement m, double cost) {
		parent = n;
		movement = m;
		
		gCost = n.getGCost() + cost;
	}
	
	public BlockPos blockPos() {
		return new BlockPos(x, y, z);
	}
	
	public int distanceSqr(BlockPos pos) {
		return distanceSqr(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public int distanceSqr(Node n) {
		return distanceSqr(n.getX(), n.getY(), n.getZ());
	}
	
	public int distanceSqr(int x, int y, int z) {
		int dx = x - this.x;
		int dy = y - this.y;
		int dz = z - this.z;
		
		int dis = dx * dx + dy * dy + dz * dz;
		
		return dis;
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
	
	public Movement getMovement() {
		return movement;
	}
	
	public BlockType getType() {
		return type;
	}
	
	public double getGCost() {
		return gCost;
	}
	
	public double getHCost() {
		return hCost;
	}
	
	public static long posAsLong(int x, int y, int z) {
		return BlockPos.asLong(x, y, z);
	}
	
}
