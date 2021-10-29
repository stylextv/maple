package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.waypoint.Waypoint;
import de.stylextv.maple.waypoint.WaypointTag;
import de.stylextv.maple.waypoint.Waypoints;

public class LostCommand extends Command {
	
	public LostCommand() {
		super("lost", "Travel to the nearest waypoint.");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		Waypoint p = Waypoints.nearestByTag(WaypointTag.USER);
		
		if(p == null) {
			ChatUtil.send("§cNo waypoints available!");
			
			return true;
		}
		
		Waypoints.gotoWaypoint(p);
		
		return true;
	}
	
}
