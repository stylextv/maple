package de.stylextv.lynx.command;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.util.math.BlockPos;

public class ArgumentHelper {
	
	private static final String INT_REGEX = "-?\\d+";
	private static final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";
	
	public static Integer toCoordinate(String s, int i) {
		int base = 0;
		
		if(s.startsWith("~")) {
			s = s.substring(1);
			
			BlockPos pos = PlayerContext.blockPosition();
			
			if(i == 0) base = pos.getX();
			else if(i == 1) base = pos.getY();
			else if(i == 2) base = pos.getZ();
		}
		
		if(s.isEmpty()) return base;
		
		Integer offset = toInt(s);
		
		if(offset == null) return null;
		
		return base + offset;
	}
	
	public static Integer toInt(String s) {
		if(!isInt(s)) return null;
		
		return Integer.parseInt(s);
	}
	
	public static Float toFloat(String s) {
		if(!isNumeric(s)) return null;
		
		return Float.parseFloat(s);
	}
	
	private static boolean isInt(String s) {
		return s.matches(INT_REGEX);
	}
	
	private static boolean isNumeric(String s) {
		return s.matches(NUMERIC_REGEX);
	}
	
}
