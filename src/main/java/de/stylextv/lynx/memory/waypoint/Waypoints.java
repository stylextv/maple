package de.stylextv.lynx.memory.waypoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.stylextv.lynx.config.ConfigHelper;
import de.stylextv.lynx.pathing.calc.goal.BlockGoal;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import net.minecraft.util.math.BlockPos;

public class Waypoints {
	
	private static final String FILE_NAME = "waypoints";
	
	private static List<Waypoint> waypoints;
	
	public static void addWaypoint(String name, String levelName, BlockPos pos) {
		Waypoint p = new Waypoint(name, levelName, pos);
		
		waypoints.add(p);
		
		save();
	}
	
	public static void gotoWaypoint(Waypoint p) {
		BlockPos pos = p.getPos();
		
		Goal goal = new BlockGoal(pos);
		
		MovementExecutor.gotoGoal(goal);
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
	
	public static Waypoint getWaypoint(String name) {
		for(Waypoint p : waypoints) {
			if(p.getName().equals(name)) return p;
		}
		
		return null;
	}
	
	public static List<Waypoint> getWaypoints() {
		return waypoints;
	}
	
}