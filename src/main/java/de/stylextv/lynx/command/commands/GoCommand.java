package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.SearchExecutor;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.util.ChatUtil;

public class GoCommand extends Command {
	
	public GoCommand() {
		super("go", "Starts moving to the set goal.", "start");
	}
	
	@Override
	public boolean execute(String[] args) {
		Goal goal = MemoryManager.getGoal();
		
		if(goal == null) {
			ChatUtil.send("§cNo goal present!");
			
			return true;
		}
		
		SearchExecutor.startSearch();
		
		ChatUtil.send("Started. §8(Goal: " + goal + ")");
		
		return true;
	}
	
}
