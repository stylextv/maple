package de.stylextv.maple.pathing;

import de.stylextv.maple.pathing.calc.Path;
import de.stylextv.maple.pathing.calc.goal.Goal;

public class PathingStatus {
	
	private PathingState state = PathingState.IDLE;
	
	private Goal goal;
	
	private Path path;
	
	public void clear() {
		setGoal(null);
		
		path = null;
	}
	
	public void setGoal(Goal g) {
		goal = g;
		
		boolean b = g == null;
		
		state = b ? PathingState.IDLE : PathingState.GOING;
	}
	
	public boolean goalMatches(Goal g) {
		if(goal == null) return false;
		
		return goal.equals(g);
	}
	
	public boolean isIdle() {
		return !isPathing() && !isAtGoal();
	}
	
	public boolean isPathing() {
		return state == PathingState.GOING;
	}
	
	public boolean isAtGoal() {
		return state == PathingState.AT_GOAL;
	}
	
	public PathingState getState() {
		return state;
	}
	
	public void setState(PathingState state) {
		this.state = state;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
	public Path getPath() {
		return path;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
}
