package de.stylextv.maple.util;

public class ExceptionUtil {
	
	public static void catchEverything(Runnable r) {
		catchEverything(r, true);
	}
	
	public static void catchEverything(Runnable r, boolean print) {
		catchEverything(r, null, print);
	}
	
	public static void catchEverything(Runnable r, Runnable catchCallback) {
		catchEverything(r, catchCallback, false);
	}
	
	public static void catchEverything(Runnable r, Runnable catchCallback, boolean print) {
		try {
			
			r.run();
			
		} catch(Exception ex) {
			
			catchCallback.run();
			
			if(print) ex.printStackTrace();
		}
	}
	
}
