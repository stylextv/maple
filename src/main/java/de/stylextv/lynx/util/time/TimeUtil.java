package de.stylextv.lynx.util.time;

public class TimeUtil {
	
	public static long ticksToMS(double ticks) {
		return Math.round(ticks * 50);
	}
	
}
