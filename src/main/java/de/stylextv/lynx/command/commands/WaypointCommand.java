package de.stylextv.lynx.command.commands;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.command.ArgumentHelper;
import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.memory.waypoint.Waypoint;
import de.stylextv.lynx.memory.waypoint.Waypoints;
import de.stylextv.lynx.util.chat.ChatUtil;
import net.minecraft.util.math.BlockPos;

public class WaypointCommand extends Command {
	
	private static final String[] USAGES = new String[] {
			"create <name> [x y z]",
			"delete <name>",
			"list [page]",
			"goto <name>"
	};
	
	public WaypointCommand() {
		super("waypoint", "Used to create and travel to waypoints.", "wp");
	}
	
	@Override
	public boolean execute(String[] args) {
		int l = args.length;
		
		if(l == 0) return false;
		
		String s = args[0];
		
		if(s.equalsIgnoreCase("list")) {
			
			List<Waypoint> waypoints = Waypoints.getWaypoints();
			
			if(waypoints.isEmpty()) {
				ChatUtil.send("§cNo waypoints available!");
				
				return true;
			}
			
			Integer page = null;
			
			if(args.length > 1) {
				page = ArgumentHelper.toInt(args[1]);
			}
			
			if(page == null) page = 1;
			
			List<String> list = new ArrayList<>();
			
			for(Waypoint p : waypoints) {
				
				if(!p.isInWorld()) continue;
				
				String name = p.getName();
				BlockPos pos = p.getPos();
				
				String s2 = name + " §8(" + pos + ")";
				
				list.add(s2);
			}
			
			ChatUtil.send("Waypoints:");
			
			String command = getName() + " list";
			
			ChatUtil.sendList(list, 7, page, command);
			
			return true;
		}
		
		if(l < 2) return false;
		
		String name = args[1];
		
		String levelName = WorldContext.getLevelName();
		
		Waypoint p = Waypoints.getWaypoint(name, levelName);
		
		if(s.equalsIgnoreCase("create")) {
			
			if(p != null) {
				ChatUtil.send("§cThat name is already in use!");
				
				return true;
			}
			
			BlockPos pos = PlayerContext.feetPosition();
			
			if(l > 2) {
				if(l < 5) return false;
				
				Integer x = ArgumentHelper.toCoordinate(args[2], 0);
				Integer y = ArgumentHelper.toCoordinate(args[3], 1);
				Integer z = ArgumentHelper.toCoordinate(args[4], 2);
				
				if(x == null || y == null || z == null) return false;
				
				pos = new BlockPos(x, y, z);
			}
			
			Waypoints.addWaypoint(name, levelName, pos);
			
			ChatUtil.send("Waypoint §o" + name + " §7added.");
			
			return true;
		}
		
		if(p == null) {
			ChatUtil.send("§cWaypoint not found!");
			
			return true;
		}
		
		if(s.equalsIgnoreCase("delete")) {
			
			Waypoints.removeWaypoint(p);
			
			name = p.getName();
			
			ChatUtil.send("Waypoint §o" + name + " §7removed.");
			
			return true;
		}
		
		if(s.equalsIgnoreCase("goto")) {
			
			Waypoints.gotoWaypoint(p);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String[] getUsages() {
		return USAGES;
	}
	
}
