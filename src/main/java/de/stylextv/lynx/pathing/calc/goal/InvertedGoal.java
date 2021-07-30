package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;

public class InvertedGoal extends Goal {
	
	private Goal goal;
	
	public InvertedGoal(Goal goal) {
		this.goal = goal;
	}
	
	@Override
	public int heuristic(Node n) {
		return -goal.heuristic(n);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("InvertedGoal{goal=%s}", goal);
	}
	
}
