package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.input.controller.BuildingController;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.ChatUtil;

public class StopCommand extends Command {
	
	public StopCommand() {
		super("stop", "Stops all actions.", "cancel");
	}
	
	@Override
	public boolean execute(String[] args) {
		MovementExecutor.stop();
		
		BuildingController.stop();
		
		ChatUtil.send("Stopped.");
		
		return true;
	}
	
}
