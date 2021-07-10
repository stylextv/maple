package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.ChatUtil;

public class GoCommand extends Command {
	
	public GoCommand() {
		super("go", "Starts moving to the set goal.");
	}
	
	@Override
	public void execute(String[] args) {
		Goal goal = MemoryManager.getGoal();
		
		if(goal == null) {
			ChatUtil.sendToUser("§cNo goal present!");
			
			return;
		}
		
		MovementExecutor.gotoGoal(goal);
		
		ChatUtil.sendToUser("Started. §8(Goal: " + goal + ")");
	}
	
}
