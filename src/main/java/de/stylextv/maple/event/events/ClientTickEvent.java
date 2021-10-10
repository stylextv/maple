package de.stylextv.maple.event.events;

import de.stylextv.maple.event.Event;
import de.stylextv.maple.event.EventListener;

public class ClientTickEvent extends Event {
	
	@Override
	public void callListener(EventListener l) {
		l.onClientTick(this);
	}
	
}
