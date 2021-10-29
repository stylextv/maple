package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
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
	public boolean execute(ArgumentList args) {
		if(args.hasExactly(0)) return false;
		
		String s = args.get(0);
		
		EntityFilter filter = EntityFilter.fromString(s);
		
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
