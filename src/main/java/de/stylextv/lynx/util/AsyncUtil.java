package de.stylextv.lynx.util;

public class AsyncUtil {
	
	public static void runAsync(Runnable r) {
		new Thread(r).start();
	}
	
}
