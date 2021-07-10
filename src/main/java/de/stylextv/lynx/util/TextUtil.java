package de.stylextv.lynx.util;

public class TextUtil {
	
	public static String combine(String[] arr, String seperator) {
		String s = "";
		
		for(int i = 0; i < arr.length; i++) {
			if(i != 0) s += seperator;
			
			s += arr[i];
		}
		
		return s;
	}
	
}
