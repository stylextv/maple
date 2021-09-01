package de.stylextv.lynx.util.time;

public class TimeUtil {
	
	public static String format(long time) {
		return TimeFormat.format(time);
	}
	
	public static long ticksToMS(double ticks) {
		return Math.round(ticks * 50);
	}
	
}
