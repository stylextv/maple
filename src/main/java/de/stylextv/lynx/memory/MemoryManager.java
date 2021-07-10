package de.stylextv.lynx.memory;

import de.stylextv.lynx.pathing.calc.goal.Goal;

public class MemoryManager {
	
	private static Goal goal;
	
	public static void setGoal(Goal g) {
		goal = g;
	}
	
	public static Goal getGoal() {
		return goal;
	}
	
}
