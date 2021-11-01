package de.stylextv.maple.pathing;

import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.calc.Path;
import de.stylextv.maple.pathing.calc.PathState;
import de.stylextv.maple.pathing.calc.SearchExecutor;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.movement.MovementExecutor;

public class PathingExecutor {
	
	private static PathingStatus status = new PathingStatus();
	
	public static void startPathing(Goal g) {
		status.setGoal(g);
		
		SearchExecutor.startSearch(g);
	}
	
	public static void stopPathing() {
		SearchExecutor.stopSearch();
		
		status.clear();
	}
	
	public static void processCommand(PathingCommand c) {
		if(c == null) {
			
			stopPathing();
			
			return;
		}
		
		PathingCommandType type = c.getType();
		
		if(type == PathingCommandType.PAUSE) {
			
			boolean safe = MovementExecutor.isSafeToPause();
			
			if(safe) MovementExecutor.setPaused(true);
			
			return;
		}
		
		MovementExecutor.setPaused(false);
		
		if(type == PathingCommandType.CANCEL) {
			
			stopPathing();
			
			return;
		}
		
		if(type == PathingCommandType.DEFER) return;
		
		Goal goal = c.getGoal();
		
		boolean idle = status.isIdle();
		
		boolean forceStart = idle || type == PathingCommandType.PATH_TO_GOAL;
		
		if(forceStart) {
			
			startPathing(goal);
			
			return;
		}
		
		Path path = getPath();
		
		PathState state = path.getState();
		
		boolean foundGoal = state == PathState.AT_GOAL;
		
		Node destination = path.lastNode();
		
		boolean destinationValid = goal.isFinalNode(destination);
		
		if(type == PathingCommandType.REVALIDATE_GOAL) {
			
			boolean invalid = foundGoal && !destinationValid;
			
			if(invalid) startPathing(goal);
			
			return;
		}
		
		if(type == PathingCommandType.FORCE_REVALIDATE_GOAL) {
			
			boolean invalid = !destinationValid && !status.goalMatches(goal);
			
			if(invalid) startPathing(goal);
			
			return;
		}
	}
	
	public static void onRenderTick() {
		MovementExecutor.followPath(getPath());
	}
	
	public static Goal getGoal() {
		return status.getGoal();
	}
	
	public static Path getPath() {
		return status.getPath();
	}
	
	public static void setPath(Path path) {
		status.setPath(path);
	}
	
	public static PathingStatus getStatus() {
		return status;
	}
	
}
