package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.util.chat.ChatUtil;

public class StopCommand extends Command {
	
	public StopCommand() {
		super("stop", "Stops the current task.", "cancel");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		if(!TaskManager.hasTask()) {
			
			ChatUtil.send("§cNo task present!");
			
			return true;
		}
		
		TaskManager.stopTask();
		
		ChatUtil.send("Stopped.");
		
		return true;
	}
	
}
