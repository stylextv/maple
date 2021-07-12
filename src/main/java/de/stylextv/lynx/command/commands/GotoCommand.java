package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.SearchExecutor;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.util.ChatUtil;

public class GotoCommand extends Command {
	
	public GotoCommand() {
		super("goto", "Sets a new goal and starts moving to it.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Goal goal = Goal.fromArgs(args);
		
		if(goal == null) return false;
		
		MemoryManager.setGoal(goal);
		
		SearchExecutor.startSearch();
		
		ChatUtil.send("Started.");
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return Goal.getUsages();
	}
	
}
