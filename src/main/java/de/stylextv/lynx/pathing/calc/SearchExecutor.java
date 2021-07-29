package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.ChatUtil;
import de.stylextv.lynx.util.async.AsyncUtil;

public class SearchExecutor {
	
	private static final long SEARCH_TIMEOUT = 1000;
	
	private static final long SLEEP_TIME = 25;
	
	private static PathFinder finder;
	
	public static void startSearch() {
		stopSearch();
		
		AsyncUtil.runAsync(() -> {
			
			Goal goal = MemoryManager.getGoal();
			
			Path path = new Path();
			
			boolean b = true;
			
			while(true) {
				
				if(!path.needsNewSegment()) {
					
					AsyncUtil.sleep(SLEEP_TIME);
					
					continue;
				}
				
				finder = new PathFinder(goal);
				
				PathSegment segment = finder.find(path.lastPosition(), SEARCH_TIMEOUT);
				
				System.out.println(segment == null ? -1 : segment.length());
				
				boolean paused = finder.wasPaused();
				
				finder = null;
				
				if(segment == null) {
					
					if(b) ChatUtil.send("§cCan't find any path!");
					
					break;
				}
				
				path.add(segment);
				
				if(b) {
					MovementExecutor.followPath(path);
					
					b = false;
				}
				
				if(!paused) break;
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
