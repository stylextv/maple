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
	
	public static String findBracket(String s, char ch1, char ch2) {
		return findBracket(s, ch1, ch2, false);
	}
	
	public static String findBracket(String s, char ch1, char ch2, boolean stopOnSpace) {
		boolean b = false;
		
		String content = "";
		
		for(char ch : s.toCharArray()) {
			
			if(ch == ' ' && stopOnSpace && !b) return null;
			
			if(ch == ch2 && b) break;
			
			if(ch == ch1) {
				
				b = true;
				
				continue;
			}
			
			if(b) content += ch;
		}
		
		if(!b) return null;
		
		return content;
	}
	
}
