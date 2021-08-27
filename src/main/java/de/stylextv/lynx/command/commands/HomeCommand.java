package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.waypoint.Waypoint;
import de.stylextv.lynx.memory.waypoint.Waypoints;
import de.stylextv.lynx.util.chat.ChatUtil;

public class HomeCommand extends Command {
	
	public HomeCommand() {
		super("home", "Travel to the waypoint named §ohome§7.", "h");
	}
	
	@Override
	public boolean execute(String[] args) {
		Waypoint p = Waypoints.getWaypoint("home");
		
		if(p == null) {
			ChatUtil.send("§cWaypoint not found!");
			
			return true;
		}
		
		Waypoints.gotoWaypoint(p);
		
		return true;
	}
	
}
