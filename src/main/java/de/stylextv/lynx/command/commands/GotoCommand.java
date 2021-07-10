package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.ChatUtil;

public class GotoCommand extends Command {
	
	public GotoCommand() {
		super("goto", "Sets a new goal and starts moving to it.");
	}
	
	@Override
	public void execute(String[] args) {
		Goal goal = Goal.fromArgs(args);
		
		if(goal == null) {
			ChatUtil.sendToUser("§cInvalid arguments!");
			
			return;
		}
		
		MemoryManager.setGoal(goal);
		
		ChatUtil.sendToUser("Goal set. §8(" + goal + ")");
		
		MovementExecutor.gotoGoal(goal);
		
		ChatUtil.sendToUser("Started.");
	}
	
	@Override
	public String[] getUsages() {
		return Goal.getUsages();
	}
	
}
