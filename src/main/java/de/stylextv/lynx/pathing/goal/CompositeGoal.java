package de.stylextv.lynx.pathing.goal;

import de.stylextv.lynx.pathing.Node;

public class CompositeGoal implements IGoal {
	
	private IGoal[] goals;
	
	public CompositeGoal(IGoal... goals) {
		this.goals = goals;
	}
	
	@Override
	public int calcHeuristic(Node n) {
		int cost = Integer.MAX_VALUE;
		
		for(IGoal g : goals) {
			int h = g.calcHeuristic(n);
			
			if(h < cost) cost = h;
		}
		
		return cost;
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		for(IGoal g : goals) {
			if(g.isFinalNode(n)) return true;
		}
		
		return false;
	}
	
}
