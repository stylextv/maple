package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.Command;
import de.stylextv.maple.memory.MemoryManager;
import de.stylextv.maple.pathing.calc.goal.Goal;

public class GoalCommand extends Command {
	
	public GoalCommand() {
		super("goal", "Sets a new goal.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Goal goal = Goal.fromArgs(args);
		
		if(goal == null) return false;
		
		MemoryManager.setGoal(goal);
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return Goal.getUsages();
	}
	
}