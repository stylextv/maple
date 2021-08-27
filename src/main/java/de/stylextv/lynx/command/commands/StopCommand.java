package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.pathing.calc.SearchExecutor;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.chat.ChatUtil;

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
