package de.stylextv.lynx.util;

public class AsyncUtil {
	
	public static void runLaterAsync(Runnable r, long delay) {
		runAsync(() -> {
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
			r.run();
		});
	}
	
	public static void runAsync(Runnable r) {
		new Thread(r).start();
	}
	
}
