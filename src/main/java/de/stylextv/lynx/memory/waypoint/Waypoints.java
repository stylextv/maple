package de.stylextv.lynx.memory.waypoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.stylextv.lynx.config.ConfigHelper;
import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.goal.BlockGoal;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.ChatUtil;
import net.minecraft.util.math.BlockPos;

public class Waypoints {
	
	private static final String FILE_NAME = "waypoints";
	
	private static List<Waypoint> waypoints;
	
	public static void addWaypoint(String name, String levelName, BlockPos pos) {
		Waypoint p = new Waypoint(name, levelName, pos);
		
		waypoints.add(p);
		
		save();
	}
	
	public static void removeWaypoint(Waypoint p) {
		waypoints.remove(p);
		
		save();
	}
	
	public static void gotoWaypoint(Waypoint p) {
		BlockPos pos = p.getPos();
		
		Goal goal = new BlockGoal(pos);
		
		MemoryManager.setGoal(goal);
		
		MovementExecutor.gotoGoal();
		
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
	
	public static Waypoint nearest() {
		Waypoint point = null;
		
		double dis = 0;
		
		for(Waypoint p : waypoints) {
			
			if(!p.isInWorld()) continue;
			
			double d = p.distanceSqr();
			
			if(point == null || d < dis) {
				
				point = p;
				dis = d;
			}
		}
		
		return point;
	}
	
	public static Waypoint getWaypoint(String name) {
		String levelName = WorldContext.getLevelName();
		
		return getWaypoint(name, levelName);
	}
	
	public static Waypoint getWaypoint(String name, String levelName) {
		for(Waypoint p : waypoints) {
			
			boolean b1 = p.getName().equalsIgnoreCase(name);
			boolean b2 = p.getLevelName().equalsIgnoreCase(levelName);
			
			if(b1 && b2) {
				return p;
			}
		}
		
		return null;
	}
	
	public static List<Waypoint> getWaypoints() {
		return waypoints;
	}
	
}
