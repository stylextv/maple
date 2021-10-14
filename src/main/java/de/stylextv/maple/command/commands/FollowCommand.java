package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.Command;
import de.stylextv.maple.task.Task;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.task.tasks.FollowTask;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.entity.EntityFilter;

public class FollowCommand extends Command {
	
	private static final String[] USAGES = new String[] {
			"<entity_type>",
			"<name>"
	};
	
	public FollowCommand() {
		super("follow", "Starts following a specified entity.");
	}
	
	@Override
	public boolean execute(String[] args) {
		if(args.length == 0) return false;
		
		EntityFilter filter = EntityFilter.fromString(args[0]);
		
		if(filter == null) return false;
		
		Task task = new FollowTask(filter);
		
		TaskManager.startTask(task);
		
		ChatUtil.send("Started following.");
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return USAGES;
	}
	
}
