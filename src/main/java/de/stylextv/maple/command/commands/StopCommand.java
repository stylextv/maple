package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.Command;
import de.stylextv.maple.pathing.calc.SearchExecutor;
import de.stylextv.maple.pathing.movement.MovementExecutor;
import de.stylextv.maple.util.chat.ChatUtil;

public class StopCommand extends Command {
	
	public StopCommand() {
		super("stop", "Stops all actions.", "cancel");
	}
	
	@Override
	public boolean execute(String[] args) {
		MovementExecutor.stop();
		
		SearchExecutor.stopSearch();
		
		ChatUtil.send("Stopped.");
		
		return true;
	}
	
}
