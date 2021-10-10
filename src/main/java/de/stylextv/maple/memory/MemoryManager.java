package de.stylextv.maple.memory;

import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.util.chat.ChatUtil;

public class MemoryManager {
	
	private static Goal goal;
	
	public static void setGoal(Goal g) {
		goal = g;
		
		ChatUtil.send("Goal set. §8(" + goal + ")");
	}
	
	public static Goal getGoal() {
		return goal;
	}
	
}
