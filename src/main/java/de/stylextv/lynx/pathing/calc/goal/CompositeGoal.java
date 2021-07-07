package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;

public class CompositeGoal extends Goal {
	
	private Goal[] goals;
	
	public CompositeGoal(Goal... goals) {
		this.goals = goals;
	}
	
	@Override
	public int heuristic(Node n) {
		int cost = Integer.MAX_VALUE;
		
		for(Goal g : goals) {
			int h = g.heuristic(n);
			
			if(h < cost) cost = h;
		}
		
		return cost;
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		for(Goal g : goals) {
			if(g.isFinalNode(n)) return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		String s = "";
		
		int l = goals.length;
		
		for(int i = 0; i < l; i++) {
			s += goals[i];
			
			if(i + 1 < l) s += ",";
		}
		
		return String.format("CompositeGoal{goals=[%s]}", s);
	}
	
}
