package de.stylextv.lynx.command;

import de.stylextv.lynx.pathing.goal.AxisGoal;
import de.stylextv.lynx.pathing.goal.Goal;
import de.stylextv.lynx.state.StateManager;
import de.stylextv.lynx.util.ChatUtil;

public class GoalCommand extends Command {
	
	public GoalCommand() {
		super("goal");
	}
	
	@Override
	public void execute(String[] args) {
		Goal goal = new AxisGoal();
		
		StateManager.setGoal(goal);
		
		ChatUtil.sendToUser("Goal set. §8(" + goal + ")");
	}
	
}
