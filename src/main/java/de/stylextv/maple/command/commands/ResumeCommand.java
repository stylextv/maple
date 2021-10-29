package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.util.chat.ChatUtil;

public class ResumeCommand extends Command {
	
	public ResumeCommand() {
		super("resume", "Resumes the current task.");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		if(!TaskManager.hasTask()) {
			
			ChatUtil.send("§cNo task present!");
			
			return true;
		}
		
		TaskManager.resumeTask();
		
		ChatUtil.send("Resumed.");
		
		return true;
	}
	
}
