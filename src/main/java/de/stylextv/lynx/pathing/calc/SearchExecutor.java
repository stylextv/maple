package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.async.AsyncUtil;

public class SearchExecutor {
	
	private static final long SEARCH_TIMEOUT = 1000;
	
	private static final long SLEEP_TIME = 500;
	
	private static PathFinder finder;
	
	public static void startSearch() {
		stopSearch();
		
		AsyncUtil.runAsync(() -> {
			
			Goal goal = MemoryManager.getGoal();
			
			Path path = new Path();
			
			MovementExecutor.followPath(path);
			
			while(true) {
				
				if(!path.needsNewSegment()) {
					
					AsyncUtil.sleep(SLEEP_TIME);
					
					continue;
				}
				
				finder = new PathFinder(goal);
				
				PathSegment segment = finder.find(path.lastPosition(), SEARCH_TIMEOUT);
				
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
