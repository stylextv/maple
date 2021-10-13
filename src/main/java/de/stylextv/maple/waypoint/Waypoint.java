package de.stylextv.maple.waypoint;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.util.time.TimeFormat;
import net.minecraft.util.math.BlockPos;

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
	
	public double distance() {
		double dis = squaredDistance();
		
		return Math.sqrt(dis);
	}
	
	public double squaredDistance() {
		if(!isInWorld()) return Double.POSITIVE_INFINITY;
		
		return PlayerContext.squaredDistanceTo(pos);
	}
	
	public boolean isInWorld() {
		String name = WorldContext.getLevelName();
		
		return levelName.equals(name);
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
