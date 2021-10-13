package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.Command;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.util.chat.ChatUtil;

public class GotoCommand extends Command {
	
	public GotoCommand() {
		super("goto", "Sets a new goal and starts moving to it.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Goal goal = Goal.fromArgs(args);
		
		if(goal == null) return false;
		
		TaskManager.gotoGoal(goal);
		
		ChatUtil.send("Started.");
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return Goal.getUsages();
	}
	
}
