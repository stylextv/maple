package de.stylextv.maple.util.async;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncUtil {
	
	private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(4, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
	
	public static TaskInfo loopAsync(Runnable r, long delay) {
		return loopAsync(r, 0, delay);
	}
	
	public static TaskInfo loopAsync(Runnable r, long initialDelay, long delay) {
		return loopAsync(r, null, initialDelay, delay);
	}
	
	public static TaskInfo loopAsync(Runnable r, Runnable killCallback, long initialDelay, long delay) {
		TaskInfo info = new TaskInfo();
		
		info.setTask(() -> {
			
			sleep(initialDelay);
			
			while(!info.isKilled()) {
				r.run();
				
				sleep(delay);
			}
			
			if(killCallback != null) killCallback.run();
		});
		
		runAsync(info);
		
		return info;
	}
	
	public static void runLaterAsync(Runnable r, long delay) {
		runAsync(() -> {
			
			sleep(delay);
			
			r.run();
		});
	}
	
	public static void runAsync(Runnable r) {
		Runnable wrapped = () -> {
			try {
				
				r.run();
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		};
		
		THREAD_POOL.execute(wrapped);
	}
	
	public static void sleep(long delay) {
		try {
			
			Thread.sleep(delay);
			
		} catch (InterruptedException ex) {}
	}
	
}
