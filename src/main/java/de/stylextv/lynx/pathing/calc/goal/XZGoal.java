package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;

public class XZGoal extends Goal {
	
	private int x;
	private int z;
	
	public XZGoal(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	@Override
	public int heuristic(Node n) {
		int disX = Math.abs(n.getX() - x);
		int disZ = Math.abs(n.getZ() - z);
		
		int dis = disX + disZ;
		
		return dis * Cost.COST_PER_UNIT;
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return n.getX() == x && n.getZ() == z;
	}
	
	@Override
	public String toString() {
		return String.format("XZGoal{x=%s, z=%s}", x, z);
	}
	
}
