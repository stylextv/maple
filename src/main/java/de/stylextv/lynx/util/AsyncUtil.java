package de.stylextv.lynx.util;

public class AsyncUtil {
	
	public static ThreadInfo loopAsync(Runnable r, long delay) {
		return loopAsync(r, 0, delay);
	}
	
	public static ThreadInfo loopAsync(Runnable r, long initialDelay, long delay) {
		return loopAsync(r, null, initialDelay, delay);
	}
	
	public static ThreadInfo loopAsync(Runnable r, Runnable killCallback, long initialDelay, long delay) {
		ThreadInfo info = new ThreadInfo();
		
		runAsync(() -> {
			
			sleep(initialDelay);
			
			while(!info.isKilled()) {
				r.run();
				
				sleep(delay);
			}
			
			if(killCallback != null) killCallback.run();
		});
		
		return info;
	}
	
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
	
	public static void sleep(long delay) {
		try {
			
			Thread.sleep(delay);
			
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
}
