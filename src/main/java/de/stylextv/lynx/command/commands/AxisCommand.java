package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.SearchExecutor;
import de.stylextv.lynx.pathing.calc.goal.AxisGoal;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.util.chat.ChatUtil;

public class AxisCommand extends Command {
	
	public AxisCommand() {
		super("axis", "Travel to the nearest axis.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Goal goal = new AxisGoal();
		
		MemoryManager.setGoal(goal);
		
		SearchExecutor.startSearch();
		
		ChatUtil.send("Started.");
		
		return true;
	}
	
}
