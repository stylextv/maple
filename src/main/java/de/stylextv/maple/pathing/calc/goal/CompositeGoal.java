package de.stylextv.maple.pathing.calc.goal;

import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.util.TextUtil;

public class CompositeGoal extends Goal {
	
	private Goal[] goals;
	
	public CompositeGoal(Goal... goals) {
		this.goals = goals;
	}
	
	@Override
	public double heuristic(Node n) {
		double cost = Double.MAX_VALUE;
		
		for(Goal g : goals) {
			double h = g.heuristic(n);
			
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
	public void render(RenderWorldEvent event) {
		for(Goal g : goals) {
			
			g.render(event);
		}
	}
	
	@Override
	public String toString() {
		String s = TextUtil.combine(goals, ",");
		
		return String.format("CompositeGoal{goals=[%s]}", s);
	}
	
}
