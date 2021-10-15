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
		
		MovementExecutor.stop();
		
		status.clear();
	}
	
	public static void processCommand(PathingCommand c) {
		if(c == null) {
			
			stopPathing();
			
			return;
		}
		
		PathingCommandType type = c.getType();
		
		if(type == PathingCommandType.PAUSE) {
			
			MovementExecutor.setPaused(true);
			
			return;
		}
		
		MovementExecutor.setPaused(false);
		
		if(type == PathingCommandType.CANCEL) {
			
			stopPathing();
			
			return;
		}
		
		if(type == PathingCommandType.DEFER) return;
		
		Goal goal = c.getGoal();
		
		if(type == PathingCommandType.PATH_TO_GOAL) {
			
			startPathing(goal);
			
			return;
		}
		
		boolean foundGoal = false;
		
		boolean destinationValid = false;
		
		Path path = MovementExecutor.getPath();
		
		if(path != null && !path.isEmpty()) {
			
			PathState state = path.getState();
			
			foundGoal = state == PathState.AT_GOAL;
			
			Node destination = path.lastNode();
			
			destinationValid = goal.isFinalNode(destination);
		}
		
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
	
	public static Goal getGoal() {
		return status.getGoal();
	}
	
	public static PathingStatus getStatus() {
		return status;
	}
	
}
