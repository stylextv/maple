package de.stylextv.lynx.command;

import de.stylextv.lynx.input.controller.MovementController;
import de.stylextv.lynx.util.ChatUtil;

public class StopCommand extends Command {
	
	public StopCommand() {
		super("stop");
	}
	
	@Override
	public void execute(String[] args) {
		MovementController.stop();
		
		ChatUtil.sendToUser("Stopped.");
	}
	
}
