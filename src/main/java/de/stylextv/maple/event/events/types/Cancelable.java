package de.stylextv.maple.event.events.types;

import de.stylextv.maple.event.Event;

public abstract class Cancelable extends Event {
	
	private boolean canceled;
	
	public boolean isCanceled() {
		return canceled;
	}
	
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
}
