package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.pathing.calc.goal.AxisGoal;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.util.chat.ChatUtil;

public class AxisCommand extends Command {
	
	public AxisCommand() {
		super("axis", "Travel to the nearest axis.");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		Goal goal = new AxisGoal();
		
		TaskManager.gotoGoal(goal);
		
		ChatUtil.send("Started.");
		
		return true;
	}
	
}
