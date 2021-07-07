package de.stylextv.lynx.state;

import de.stylextv.lynx.pathing.calc.goal.Goal;

public class StateManager {
	
	private static Goal goal;
	
	public static void setGoal(Goal g) {
		goal = g;
	}
	
	public static Goal getGoal() {
		return goal;
	}
	
}
