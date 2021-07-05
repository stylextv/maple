package de.stylextv.dwarf.pathing.goal;

import de.stylextv.dwarf.pathing.Node;

public class XZGoal implements IGoal {
	
	private int x;
	private int z;
	
	public XZGoal(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	@Override
	public int calcHeuristic(Node n) {
		int disX = Math.abs(n.getX() - x);
		int disZ = Math.abs(n.getZ() - z);
		
		int dis = disX + disZ;
		
		return dis * Node.COST_PER_UNIT;
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return n.getX() == x && n.getZ() == z;
	}
	
}
