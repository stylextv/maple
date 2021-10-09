package de.stylextv.lynx.memory.waypoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.stylextv.lynx.config.ConfigHelper;
import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.SearchExecutor;
import de.stylextv.lynx.pathing.calc.goal.BlockGoal;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.util.chat.ChatUtil;
import net.minecraft.util.math.BlockPos;

public class Waypoints {
	
	private static final String FILE_NAME = "waypoints";
	
	private static List<Waypoint> waypoints;
	
	public static boolean addWaypoint(WaypointTag tag, String levelName, BlockPos pos) {
		String name;
		
		int i = 0;
		
		while(true) {
			
			name = tag.getName();
			
			if(i != 0) name += " #" + (i + 1);
			
			boolean exists = getWaypointByName(name) != null;
			
			if(!exists) break;
			
			i++;
		}
		
		return addWaypoint(name, tag, levelName, pos);
	}
	
	public static boolean addWaypoint(String name, WaypointTag tag, String levelName, BlockPos pos) {
		Waypoint p = getWaypointByPos(pos, levelName);
		
		if(p != null) return false;
		
		p = new Waypoint(name, tag, levelName, pos);
		
		waypoints.add(p);
		
		save();
		
		return true;
	}
	
	public static void removeWaypoint(Waypoint p) {
		waypoints.remove(p);
		
		save();
	}
	
	public static void gotoWaypoint(Waypoint p) {
		BlockPos pos = p.getPos();
		
		Goal goal = new BlockGoal(pos);
		
		MemoryManager.setGoal(goal);
		
		SearchExecutor.startSearch();
		
		String name = p.getName();
		
		ChatUtil.send("Travelling to waypoint §o" + name + "§7.");
	}
	
	public static void save() {
		ConfigHelper.saveFile(FILE_NAME, waypoints());
	}
	
	public static void load() {
		waypoints = new ArrayList<>();
		
		Waypoint[] arr = ConfigHelper.loadFile(FILE_NAME, Waypoint[].class);
		
		if(arr == null) return;
		
		List<Waypoint> list = Arrays.asList(arr);
		
		waypoints.addAll(list);
	}
	
	private static Waypoint[] waypoints() {
		Waypoint[] arr = new Waypoint[0];
		
		return waypoints.toArray(arr);
	}
	
	public static Waypoint nearestByTag(WaypointTag tag) {
		Waypoint point = null;
		
		double dis = 0;
		
		for(Waypoint p : waypoints) {
			
			WaypointTag otherTag = p.getTag();
			
			if(otherTag != tag || !p.isInWorld()) continue;
			
			double d = p.squaredDistance();
			
			if(point == null || d < dis) {
				
				point = p;
				dis = d;
			}
		}
		
		return point;
	}
	
	public static Waypoint getWaypointByPos(BlockPos pos) {
		String levelName = WorldContext.getLevelName();
		
		return getWaypointByPos(pos, levelName);
	}
	
	public static Waypoint getWaypointByPos(BlockPos pos, String levelName) {
		for(Waypoint p : waypoints) {
			
			boolean b1 = p.getPos().equals(pos);
			boolean b2 = p.getLevelName().equalsIgnoreCase(levelName);
			
			if(b1 && b2) return p;
		}
		
		return null;
	}
	
	public static Waypoint getWaypointByName(String name) {
		String levelName = WorldContext.getLevelName();
		
		return getWaypointByName(name, levelName);
	}
	
	public static Waypoint getWaypointByName(String name, String levelName) {
		for(Waypoint p : waypoints) {
			
			boolean b1 = p.getName().equalsIgnoreCase(name);
			boolean b2 = p.getLevelName().equalsIgnoreCase(levelName);
			
			if(b1 && b2) return p;
		}
		
		return null;
	}
	
	public static List<Waypoint> getWaypoints() {
		return waypoints;
	}
	
}
