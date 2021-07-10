package de.stylextv.lynx.command;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.util.math.BlockPos;

public class ArgumentHelper {
	
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
		
		return base += toInt(s);
	}
	
	public static Integer toInt(String s) {
		if(!isInt(s)) return null;
		
		return Integer.parseInt(s);
	}
	
	private static boolean isInt(String s) {
		return s.matches("-?\\d+");
	}
	
}
