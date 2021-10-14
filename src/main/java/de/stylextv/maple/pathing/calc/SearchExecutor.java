package de.stylextv.maple.pathing.calc;

import de.stylextv.maple.pathing.calc.favoring.Favoring;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.movement.MovementExecutor;
import de.stylextv.maple.util.async.AsyncUtil;

public class SearchExecutor {
	
	private static final long INITIAL_TIMEOUT = 500;
	
	private static final long INITIAL_FAILURE_TIMEOUT = 2000;
	
	private static final long PLAN_AHEAD_TIMEOUT = 4000;
	
	private static final long PLAN_AHEAD_FAILURE_TIMEOUT = 5000;
	
	private static final long CALCULATION_TIME_BUFFER = 2000;
	
	private static final long SLEEP_TIME = 100;
	
	private static PathFinder finder;
	
	public static void startSearch(Goal goal) {
		stopSearch();
		
		AsyncUtil.runAsync(() -> {
			
			Path path = new Path();
			
			MovementExecutor.followPath(path);
			
			while(true) {
				
				boolean required = path.equals(MovementExecutor.getPath());
				
				if(!required) break;
				
				boolean initial = path.isEmpty();
				
				long primaryTimeout = initial ? INITIAL_TIMEOUT : PLAN_AHEAD_TIMEOUT;
				long failureTimeout = initial ? INITIAL_FAILURE_TIMEOUT : PLAN_AHEAD_FAILURE_TIMEOUT;
				
				long requiredTime = primaryTimeout + CALCULATION_TIME_BUFFER;
				
				boolean sleep = path.isFinished() || path.timeLeft() > requiredTime;
				
				if(sleep) {
					
					AsyncUtil.sleep(SLEEP_TIME);
					
					continue;
				}
				
				Favoring favoring = new Favoring(path.lastSegment());
				
				finder = new PathFinder(goal, favoring);
				
				PathSegment segment = finder.find(path.lastPosition(), primaryTimeout, failureTimeout);
				
				boolean paused = finder.wasPaused();
				
				finder = null;
				
				if(segment == null) {
					
					path.setState(PathState.FAILED);
					
					continue;
				}
				
				boolean empty = segment.isEmpty();
				
				if(!empty) path.add(segment);
				
				if(!paused || empty) path.setState(PathState.AT_GOAL);
			}
		});
	}
	
	public static void stopSearch() {
		if(!isInSearch()) return;
		
		finder.stop();
	}
	
	public static PathSegment getLastConsideration() {
		if(!isInSearch()) return null;
		
		return finder.lastConsideredPath();
	}
	
	public static PathSegment getBestSoFar() {
		if(!isInSearch()) return null;
		
		return finder.bestSoFar();
	}
	
	public static boolean isInSearch() {
		return finder != null;
	}
	
}
