package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;

public class WaypointCommand extends Command {
	
	public WaypointCommand() {
		super("waypoint", "Used to create and travel to waypoints.");
	}
	
	@Override
	public void execute(String[] args) {
		
	}
	
	@Override
	public String[] getUsages() {
		return new String[] {"[command]"};
	}
	
}
