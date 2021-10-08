package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.memory.waypoint.Waypoint;
import de.stylextv.lynx.memory.waypoint.WaypointTag;
import de.stylextv.lynx.memory.waypoint.Waypoints;
import de.stylextv.lynx.util.chat.ChatUtil;

public class HomeCommand extends Command {
	
	public HomeCommand() {
		super("home", "Travel to the nearest waypoint marked as §oHOME§7.", "h");
	}
	
	@Override
	public boolean execute(String[] args) {
		Waypoint p = Waypoints.nearestByTag(WaypointTag.HOME);
		
		if(p == null) {
			ChatUtil.send("§cNo waypoints available!");
			
			return true;
		}
		
		Waypoints.gotoWaypoint(p);
		
		return true;
	}
	
}
