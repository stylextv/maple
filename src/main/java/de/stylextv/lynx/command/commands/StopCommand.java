package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.input.controller.BreakController;
import de.stylextv.lynx.input.controller.PlaceController;
import de.stylextv.lynx.pathing.calc.SearchExecutor;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.ChatUtil;

public class StopCommand extends Command {
	
	public StopCommand() {
		super("stop", "Stops all actions.", "cancel");
	}
	
	@Override
	public boolean execute(String[] args) {
		SearchExecutor.stopSearch();
		
		MovementExecutor.stop();
		
		BreakController.stop();
		PlaceController.stop();
		
		ChatUtil.send("Stopped.");
		
		return true;
	}
	
}
