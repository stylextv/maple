package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.Command;
import de.stylextv.maple.memory.waypoint.Waypoint;
import de.stylextv.maple.memory.waypoint.WaypointTag;
import de.stylextv.maple.memory.waypoint.Waypoints;
import de.stylextv.maple.util.chat.ChatUtil;

public class LostCommand extends Command {
	
	public LostCommand() {
		super("lost", "Travel to the nearest waypoint.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Waypoint p = Waypoints.nearestByTag(WaypointTag.USER);
		
		if(p == null) {
			ChatUtil.send("§cNo waypoints available!");
			
			return true;
		}
		
		Waypoints.gotoWaypoint(p);
		
		return true;
	}
	
}
