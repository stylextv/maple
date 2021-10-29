package de.stylextv.maple.command;

import de.stylextv.maple.context.PlayerContext;
import net.minecraft.util.math.BlockPos;

public class ArgumentHelper {
	
	private static final String INT_REGEX = "-?\\d+";
	
	private static final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";
	
	public static int toCoordinate(String s, int i) {
		int base = 0;
		
		if(s.startsWith("~")) {
			s = s.substring(1);
			
			BlockPos pos = PlayerContext.feetPosition();
			
			if(i == 0) base = pos.getX();
			else if(i == 1) base = pos.getY();
			else if(i == 2) base = pos.getZ();
		}
		
		if(s.isEmpty()) return base;
		
		int offset = toInt(s);
		
		return base + offset;
	}
	
	public static int toInt(String s) {
		return Integer.parseInt(s);
	}
	
	public static float toFloat(String s) {
		return Float.parseFloat(s);
	}
	
	public static boolean isInt(String s) {
		return s.matches(INT_REGEX);
	}
	
	public static boolean isNumeric(String s) {
		return s.matches(NUMERIC_REGEX);
	}
	
}
