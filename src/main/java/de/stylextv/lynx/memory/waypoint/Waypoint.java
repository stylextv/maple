package de.stylextv.lynx.memory.waypoint;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.util.math.BlockPos;
import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.util.time.TimeFormat;

public class Waypoint {
	
	private String name;
	
	private WaypointTag tag;
	
	private String levelName;
	
	private BlockPos pos;
	
	private long timeStamp;
	
	public Waypoint(String name, WaypointTag tag, String levelName, BlockPos pos) {
		this.name = name;
		this.tag = tag;
		this.levelName = levelName;
		this.pos = pos;
		
		this.timeStamp = System.currentTimeMillis();
	}
	
	public boolean isInWorld() {
		String name = WorldContext.getLevelName();
		
		return levelName.equals(name);
	}
	
	public double squaredDistance() {
		return PlayerContext.squaredDistanceTo(pos);
	}
	
	public String getDisplayName() {
		String date = TimeFormat.formatDate(timeStamp);
		
		return name + " §8@ " + date;
	}
	
	public String getName() {
		return name;
	}
	
	public WaypointTag getTag() {
		return tag;
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
}
