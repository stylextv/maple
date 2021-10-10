package de.stylextv.maple.util.async;

public class TaskInfo implements Runnable {
	
	private static final long WAIT_TIME = 5;
	
	private Runnable task;
	
	private Thread thread;
	
	private boolean killed;
	
	private boolean active;
	
	public void kill() {
		killed = true;
		
		if(thread == null) return;
		
		thread.interrupt();
		
		waitFor();
	}
	
	private void waitFor() {
		while(isActive()) {
			
			AsyncUtil.sleep(WAIT_TIME);
		}
	}
	
	@Override
	public void run() {
		if(isKilled()) return;
		
		active = true;
		
		thread = Thread.currentThread();
		
		task.run();
		
		active = false;
	}
	
	public Runnable getTask() {
		return task;
	}
	
	public void setTask(Runnable r) {
		this.task = r;
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public boolean isKilled() {
		return killed;
	}
	
	public boolean isActive() {
		return active;
	}
	
}
