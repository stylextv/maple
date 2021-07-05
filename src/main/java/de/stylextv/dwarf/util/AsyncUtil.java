package de.stylextv.dwarf.util;

public class AsyncUtil {
	
	public static void runAsync(Runnable r) {
		new Thread(r).start();
	}
	
}
