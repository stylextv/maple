package de.stylextv.maple.pathing;

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
		
		if(type == PathingCommandType.DEFER) return;
		
		Goal goal = c.getGoal();
		
		if(type == PathingCommandType.PATH_TO_GOAL) {
			
			startPathing(goal);
			
			return;
		}
		
		if(type == PathingCommandType.REVALIDATE_GOAL) {
			
			boolean matches = status.goalMatches(goal);
			
			if(!matches) startPathing(goal);
		}
	}
	
	public static Goal getGoal() {
		return status.getGoal();
	}
	
	public static PathingStatus getStatus() {
		return status;
	}
	
}
