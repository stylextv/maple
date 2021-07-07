package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.ChatUtil;

public class StopCommand extends Command {
	
	public StopCommand() {
		super("stop", "Stops all actions");
	}
	
	@Override
	public void execute(String[] args) {
		MovementExecutor.stop();
		
		ChatUtil.sendToUser("Stopped.");
	}
	
}
