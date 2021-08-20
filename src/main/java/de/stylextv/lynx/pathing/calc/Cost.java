package de.stylextv.lynx.pathing.calc;

public class Cost {
	
	public static final double INFINITY = 100000000;
	
	public static final double WALK_STRAIGHT = 4.6328;
	public static final double WALK_DIAGONALLY = 6.5518;
	
	public static final double SPRINT_STRAIGHT = 3.5638;
	public static final double SPRINT_DIAGONALLY = 5.04;
	
	public static final double JUMP = fallCost(1.25) - fallCost(0.25);
	
	public static final double[] FALL_N_BLOCKS = new double[255];
	
	static {
		for(int i = 0; i < FALL_N_BLOCKS.length; i++) {
			FALL_N_BLOCKS[i] = fallCost(i);
		}
	}
	
	private static double fallCost(double dis) {
		if(dis == 0) return 0;
		
		return Math.sqrt(dis * 100);
	}
	
}
