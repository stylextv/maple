package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.favoring.Favoring;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.async.AsyncUtil;

public class SearchExecutor {
	
	private static final long INITIAL_TIMEOUT = 500;
	
	private static final long INITIAL_FAILURE_TIMEOUT = 2000;
	
	private static final long PLAN_AHEAD_TIMEOUT = 4000;
	
	private static final long PLAN_AHEAD_FAILURE_TIMEOUT = 5000;
	
	private static final long CALCULATION_TIME_BUFFER = 2000;
	
	private static final long SLEEP_TIME = 100;
	
	private static PathFinder finder;
	
	public static void startSearch() {
		stopSearch();
		
		AsyncUtil.runAsync(() -> {
			
			Goal goal = MemoryManager.getGoal();
			
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
				
				path.add(segment);
				
				if(!paused) path.setState(PathState.AT_GOAL);
			}
		});
	}
	
	public static void stopSearch() {
		if(!isInSearch()) return;
		
		finder.stop();
	}
	
	public static Node getCurrentNode() {
		if(!isInSearch()) return null;
		
		return finder.getCurrentNode();
	}
	
	public static boolean isInSearch() {
		return finder != null;
	}
	
}
