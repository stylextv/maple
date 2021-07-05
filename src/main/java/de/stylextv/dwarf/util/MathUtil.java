package de.stylextv.dwarf.util;

public class MathUtil {
	
	public static float clamp(float f, float min, float max) {
		if(f < min) return min;
		if(f > max) return max;
		
		return f;
	}
	
	public static double lerp(double d, double target, double speed) {
		return d + (target - d) * speed;
	}
	
}
