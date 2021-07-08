package de.stylextv.lynx.util;

public class ThreadInfo {
	
	private boolean killed;
	
	public void kill() {
		killed = true;
	}
	
	public boolean isKilled() {
		return killed;
	}
	
}
