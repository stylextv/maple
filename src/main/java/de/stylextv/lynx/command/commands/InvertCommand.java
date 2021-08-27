package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.calc.goal.InvertedGoal;
import de.stylextv.lynx.util.chat.ChatUtil;

public class InvertCommand extends Command {
	
	public InvertCommand() {
		super("invert", "Inverts the set goal.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Goal goal = MemoryManager.getGoal();
		
		if(goal == null) {
			ChatUtil.send("§cNo goal present!");
			
			return true;
		}
		
		MemoryManager.setGoal(new InvertedGoal(goal));
		
		ChatUtil.send("Goal inverted.");
		
		return true;
	}
	
}
