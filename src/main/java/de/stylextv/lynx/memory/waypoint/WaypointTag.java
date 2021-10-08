package de.stylextv.lynx.memory.waypoint;

public enum WaypointTag {
	
	HOME("Home"),
	USER("User"),
	BED("Bed"), 
	DEATH("Death");
	
	private String name;
	
	private WaypointTag(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
