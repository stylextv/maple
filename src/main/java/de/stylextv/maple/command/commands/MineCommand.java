package de.stylextv.maple.command.commands;

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
	public boolean execute(String[] args) {
		if(args.length == 0) return false;
		
		BlockFilter filter = BlockFilter.fromString(args[0]);
		
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
