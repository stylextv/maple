package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.Command;
import de.stylextv.maple.memory.MemoryManager;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.calc.goal.InvertedGoal;
import de.stylextv.maple.util.chat.ChatUtil;

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
