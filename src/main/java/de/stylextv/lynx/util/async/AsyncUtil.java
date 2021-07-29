package de.stylextv.lynx.util.async;

public class AsyncUtil {
	
	public static ThreadInfo loopAsync(Runnable r, long delay) {
		return loopAsync(r, 0, delay);
	}
	
	public static ThreadInfo loopAsync(Runnable r, long initialDelay, long delay) {
		return loopAsync(r, null, initialDelay, delay);
	}
	
	public static ThreadInfo loopAsync(Runnable r, Runnable killCallback, long initialDelay, long delay) {
		ThreadInfo info = new ThreadInfo();
		
		Thread thread = runAsync(() -> {
			
			sleep(initialDelay);
			
			while(!info.isKilled()) {
				r.run();
				
				sleep(delay);
			}
			
			if(killCallback != null) killCallback.run();
		});
		
		info.setThread(thread);
		
		return info;
	}
	
	public static Thread runLaterAsync(Runnable r, long delay) {
		return runAsync(() -> {
			
			sleep(delay);
			
			r.run();
		});
	}
	
	public static Thread runAsync(Runnable r) {
		Thread thread = new Thread(r);
		
		thread.start();
		
		return thread;
	}
	
	public static void sleep(long delay) {
		try {
			
			Thread.sleep(delay);
			
		} catch (InterruptedException ex) {}
	}
	
}
