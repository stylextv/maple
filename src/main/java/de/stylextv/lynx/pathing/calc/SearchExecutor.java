package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.async.AsyncUtil;

public class SearchExecutor {
	
	private static final long INITIAL_TIMEOUT = 500;
	
	private static final long PLAN_AHEAD_TIMEOUT = 4000;
	
	private static final long CALCULATION_TIME_BUFFER = 6000;
	
	private static final long SLEEP_TIME = 500;
	
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
				
				long time = initial ? INITIAL_TIMEOUT : PLAN_AHEAD_TIMEOUT;
				
				long requiredTime = time + CALCULATION_TIME_BUFFER;
				
				if(path.timeLeft() > requiredTime) {
					
					AsyncUtil.sleep(SLEEP_TIME);
					
					continue;
				}
				
				finder = new PathFinder(goal);
				
				PathSegment segment = finder.find(path.lastPosition(), time);
				
				boolean paused = finder.wasPaused();
				
				finder = null;
				
				if(segment == null) break;
				
				path.add(segment);
				
				if(!paused) break;
			}
			
			path.setFinished(true);
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
