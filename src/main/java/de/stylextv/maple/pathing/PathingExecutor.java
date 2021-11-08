package de.stylextv.maple.pathing;

import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.calc.Path;
import de.stylextv.maple.pathing.calc.PathSegment;
import de.stylextv.maple.pathing.calc.SearchExecutor;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.MovementExecutor;

public class PathingExecutor {
	
	private static PathingStatus status = new PathingStatus();
	
	public static void startPathing(Goal g) {
		if(!MovementExecutor.isSafeToPause()) return;
		
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
		
		boolean pause = shouldPause();
		
		MovementExecutor.setPaused(pause);
		
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
		
		boolean destinationValid = status.destinationMatches(goal);
		
		if(type == PathingCommandType.REVALIDATE_GOAL) {
			
			boolean foundGoal = status.hasFoundGoal();
			
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
	
	private static boolean shouldPause() {
		if(!MovementExecutor.hasPath()) return false;
		
		Path path = MovementExecutor.getPath();
		
		Movement m = path.getCurrentMovement();
		
		PathSegment s = SearchExecutor.getBestSoFar();
		
		if(m == null || s == null) return false;
		
		Node source = m.getSource();
		
		return s.contains(source);
	}
	
	public static Goal getGoal() {
		return status.getGoal();
	}
	
	public static boolean isPathing() {
		return status.isPathing();
	}
	
	public static PathingStatus getStatus() {
		return status;
	}
	
}
