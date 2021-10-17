package de.stylextv.maple.pathing.calc.goal;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

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
	
	public static CompositeGoal combine(CompositeGoal goal1, CompositeGoal goal2) {
		Goal[] goals1 = goal1.getGoals();
		Goal[] goals2 = goal2.getGoals();
		
		Stream<Goal> stream1 = Arrays.stream(goals1);
		Stream<Goal> stream2 = Arrays.stream(goals2);
		
		Goal[] goals = Stream.concat(stream1, stream2).toArray(Goal[]::new);
		
		return new CompositeGoal(goals);
	}
	
	public static <T> CompositeGoal fromCollection(Collection<T> collection, Function<T, Goal> function) {
		int l = collection.size();
		
		Goal[] goals = new Goal[l];
		
		int i = 0;
		
		for(T t : collection) {
			
			Goal g = function.apply(t);
			
			goals[i] = g;
			
			i++;
		}
		
		return new CompositeGoal(goals);
	}
	
}
