package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.task.Task;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.task.tasks.GetToBlockTask;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;

public class GotoCommand extends Command {
	
	private static final String[] USAGES = new String[] {
			"<x> <y> <z> [radius]",
			"<x> <z>",
			"<y>",
			"<block_type>"
	};
	
	public GotoCommand() {
		super("goto", "Starts moving to a custom goal.");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		if(args.hasExactly(0)) return false;
		
		String s = args.get(0);
		
		BlockFilter filter = BlockFilter.fromString(s);
		
		if(filter != null) {
			
			Task task = new GetToBlockTask(filter);
			
			TaskManager.startTask(task);
			
			ChatUtil.send("Started.");
			
			return true;
		}
		
		Goal goal = Goal.fromArgs(args);
		
		if(goal == null) return false;
		
		TaskManager.gotoGoal(goal);
		
		ChatUtil.send("Started.");
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return USAGES;
	}
	
}
