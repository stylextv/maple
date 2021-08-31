package de.stylextv.lynx.util;

public class TimeUtil {
	
	// TODO enhance
	public static String format(long time) {
		return time + "ms";
	}
	
	public static long ticksToMS(double ticks) {
		return Math.round(ticks * 50);
	}
	
}
