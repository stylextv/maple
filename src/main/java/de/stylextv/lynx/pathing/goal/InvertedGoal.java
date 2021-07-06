package de.stylextv.lynx.pathing.goal;

import de.stylextv.lynx.pathing.Node;

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
		return "InvertGoal{goal=" + goal + "}";
	}
	
}
