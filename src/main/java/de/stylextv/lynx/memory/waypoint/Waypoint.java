package de.stylextv.lynx.memory.waypoint;

import net.minecraft.util.math.BlockPos;

public class Waypoint {
	
	private String name;
	
	private String levelName;
	
	private BlockPos pos;
	
	public Waypoint(String name, String levelName, BlockPos pos) {
		this.name = name;
		this.levelName = levelName;
		this.pos = pos;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
}
