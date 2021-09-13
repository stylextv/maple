package de.stylextv.lynx.event.events.types;

import de.stylextv.lynx.event.Event;

public abstract class Cancelable extends Event {
	
	private boolean canceled;
	
	public boolean isCanceled() {
		return canceled;
	}
	
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
}
