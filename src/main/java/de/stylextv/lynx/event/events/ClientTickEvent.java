package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;

public class ClientTickEvent extends Event {
	
	@Override
	public void callListener(EventListener l) {
		l.onClientTick(this);
	}
	
}
