package de.stylextv.maple.pathing.calc.goal;

import java.util.List;

import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.util.TextUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

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
	
	public boolean isEmpty() {
		return goals.length == 0;
	}
	
	@Override
	public boolean equals(Goal other) {
		if(!(other instanceof CompositeGoal)) return false;
		
		Goal[] otherGoals = ((CompositeGoal) other).getGoals();
		
		int l = otherGoals.length;
		
		if(l != goals.length) return false;
		
		for(int i = 0; i < l; i++) {
			
			Goal otherGoal = otherGoals[i];
			Goal g = goals[i];
			
			if(!otherGoal.equals(g)) return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		String s = TextUtil.combine(goals, ",");
		
		return String.format("CompositeGoal{goals=[%s]}", s);
	}
	
	public Goal[] getGoals() {
		return goals;
	}
	
	public static CompositeGoal fromPositions(List<BlockPos> positions) {
		int l = positions.size();
		
		Goal[] goals = new Goal[l];
		
		for(int i = 0; i < l; i++) {
			
			BlockPos pos = positions.get(i);
			
			Goal g = new TwoBlocksGoal(pos);
			
			goals[i] = g;
		}
		
		return new CompositeGoal(goals);
	}
	
	public static CompositeGoal fromEntities(List<Entity> entities, float dis) {
		int l = entities.size();
		
		Goal[] goals = new Goal[l];
		
		for(int i = 0; i < l; i++) {
			
			Entity e = entities.get(i);
			
			BlockPos pos = e.getBlockPos();
			
			Goal g = new NearGoal(pos, dis);
			
			goals[i] = g;
		}
		
		return new CompositeGoal(goals);
	}
	
}
