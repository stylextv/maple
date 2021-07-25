package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.AsyncUtil;

public class SearchExecutor {
	
	private static final long SEARCH_TIMEOUT = 10000;
	
	private static PathFinder finder;
	
	public static void startSearch() {
		stopSearch();
		
		AsyncUtil.runAsync(() -> {
			
			Goal goal = MemoryManager.getGoal();
			
			finder = new PathFinder(goal);
			
			Path path = finder.find(PlayerContext.feetPosition());
			
			boolean b = finder.wasStopped();
			
			finder = null;
			
			if(!b) MovementExecutor.followPath(path);
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
