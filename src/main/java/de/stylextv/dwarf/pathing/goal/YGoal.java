package de.stylextv.dwarf.pathing.goal;

import de.stylextv.dwarf.pathing.Node;

public class YGoal implements IGoal {
	
	private int y;
	
	public YGoal(int y) {
		this.y = y;
	}
	
	@Override
	public int calcHeuristic(Node n) {
		int dis = Math.abs(n.getY() - y);
		
		return dis * Node.COST_PER_UNIT;
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return n.getY() == y;
	}
	
}
