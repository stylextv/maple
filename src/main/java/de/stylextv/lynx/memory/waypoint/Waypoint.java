package de.stylextv.lynx.memory.waypoint;

import de.stylextv.lynx.context.WorldContext;
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
	
	public boolean isInWorld() {
		String name = WorldContext.getLevelName();
		
		return levelName.equals(name);
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
