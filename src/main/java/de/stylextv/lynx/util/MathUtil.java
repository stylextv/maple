package de.stylextv.lynx.util;

public class MathUtil {
	
	private static final double NATURAL_LOG_TWO = Math.log(2);
	
	public static int clamp(int i, int min, int max) {
		return (int) clamp((float) i, min, max);
	}
	
	public static float clamp(float f, float min, float max) {
		if(f < min) return min;
		if(f > max) return max;
		
		return f;
	}
	
	public static double lerp(double d, double target, double speed) {
		return d + (target - d) * speed;
	}
	
	public static double log2(double d) {
		return Math.log(d) / NATURAL_LOG_TWO;
	}
	
}
