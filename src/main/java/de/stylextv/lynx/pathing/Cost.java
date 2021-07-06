package de.stylextv.lynx.pathing;

public class Cost {
	
	public static final int INFINITY = 1000000000;
	
	public static final int COST_PER_UNIT = 10;
	
	public static final int[] MOVEMENT_COSTS = new int[] {
			10, 14, 17
	};
	
	public static final int INVALID = -1;
	
	public static final int NEUTRAL_MULTIPLIER = 1;
	
	public static final int WATER_MULTIPLIER = 3;
	
	public static final int OBSTRUCTION_MULTIPLIER = 2;
	
}
