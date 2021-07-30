package de.stylextv.lynx.util;

public class TextUtil {
	
	public static String combine(Object[] arr, String seperator) {
		String s = "";
		
		for(int i = 0; i < arr.length; i++) {
			if(i != 0) s += seperator;
			
			Object o = arr[i];
			
			s += o.toString();
		}
		
		return s;
	}
	
}
