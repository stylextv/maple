package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.state.StateManager;
import de.stylextv.lynx.util.ChatUtil;

public class GoalCommand extends Command {
	
	public GoalCommand() {
		super("goal", "Sets a new goal.");
	}
	
	@Override
	public void execute(String[] args) {
		Goal goal = Goal.fromArgs(args);
		
		if(goal == null) {
			ChatUtil.sendToUser("§cInvalid arguments!");
			
			return;
		}
		
		StateManager.setGoal(goal);
		
		ChatUtil.sendToUser("Goal set. §8(" + goal + ")");
	}
	
	@Override
	public String[] getUsages() {
		return Goal.getUsages();
	}
	
}
