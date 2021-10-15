package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.Command;
import de.stylextv.maple.task.Task;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.task.tasks.FarmTask;
import de.stylextv.maple.task.tasks.MineTask;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;

public class FarmCommand extends Command {
	
	public FarmCommand() {
		super("farm", "Starts harvesting nearby crops.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Task task = new FarmTask();
		
		TaskManager.startTask(task);
		
		ChatUtil.send("Started farming.");
		
		return true;
	}
	
}
