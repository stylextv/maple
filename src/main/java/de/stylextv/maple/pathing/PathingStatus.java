package de.stylextv.maple.pathing;

import de.stylextv.maple.pathing.calc.goal.Goal;

public class PathingStatus {
	
	private PathingState state = PathingState.IDLE;
	
	private Goal goal;
	
	public void clear() {
		setGoal(null);
	}
	
	public void setGoal(Goal g) {
		goal = g;
		
		boolean b = g == null;
		
		state = b ? PathingState.IDLE : PathingState.GOING;
	}
	
	// TODO implement!
	public boolean goalMatches(Goal goal) {
		return false;
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
	
}
