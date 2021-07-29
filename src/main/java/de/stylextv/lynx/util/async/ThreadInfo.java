package de.stylextv.lynx.util.async;

public class ThreadInfo {
	
	private static final long WAIT_TIME = 5;
	
	private Thread thread;
	
	private boolean killed;
	
	public void kill() {
		killed = true;
		
		thread.interrupt();
		
		waitFor();
	}
	
	private void waitFor() {
		while(thread.isAlive()) {
			
			AsyncUtil.sleep(WAIT_TIME);
		}
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	public boolean isKilled() {
		return killed;
	}
	
}
