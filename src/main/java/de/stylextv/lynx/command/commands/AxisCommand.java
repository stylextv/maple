package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.AxisGoal;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.ChatUtil;

public class AxisCommand extends Command {
	
	public AxisCommand() {
		super("axis", "Travel to the nearest axis.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Goal goal = new AxisGoal();
		
		MemoryManager.setGoal(goal);
		
		MovementExecutor.gotoGoal();
		
		ChatUtil.send("Started.");
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return Goal.getUsages();
	}
	
}
