package de.stylextv.lynx.command;

public class ArgumentHelper {
	
	public static Integer toInt(String s) {
		if(!isInt(s)) return null;
		
		return Integer.parseInt(s);
	}
	
	private static boolean isInt(String s) {
		return s.matches("-?\\d+");
	}
	
}
