package de.stylextv.lynx.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MathUtil {
	
	private static final double NATURAL_LOG_TWO = Math.log(2);
	
	private static final DecimalFormat FORMAT = new DecimalFormat("#.0", new DecimalFormatSymbols(Locale.US));
	
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
	
	public static String formatNumber(double d) {
		return FORMAT.format(d);
	}
	
}
