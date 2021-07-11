package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.cost.Cost;

public class YLevelGoal extends Goal {
	
	private int y;
	
	public YLevelGoal(int y) {
		this.y = y;
	}
	
	@Override
	public int heuristic(Node n) {
		int dis = Math.abs(n.getY() - y);
		
		return dis * Cost.COST_PER_UNIT;
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return n.getY() == y;
	}
	
	@Override
	public String toString() {
		return String.format("YGoal{y=%s}", y);
	}
	
}
