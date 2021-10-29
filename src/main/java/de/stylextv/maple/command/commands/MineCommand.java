package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.task.Task;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.task.tasks.MineTask;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;

public class MineCommand extends Command {
	
	public MineCommand() {
		super("mine", "Starts mining a specified block type.");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		if(args.hasExactly(0)) return false;
		
		String s = args.get(0);
		
		BlockFilter filter = BlockFilter.fromString(s);
		
		if(filter == null) return false;
		
		Task task = new MineTask(filter);
		
		TaskManager.startTask(task);
		
		ChatUtil.send("Started mining.");
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return new String[] {"<block_type>"};
	}
	
}
