package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.Command;
import de.stylextv.maple.memory.MemoryManager;
import de.stylextv.maple.pathing.calc.SearchExecutor;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.util.chat.ChatUtil;

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
