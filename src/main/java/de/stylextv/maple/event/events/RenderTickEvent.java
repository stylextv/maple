package de.stylextv.maple.event.events;

import de.stylextv.maple.event.Event;
import de.stylextv.maple.event.EventListener;

public class RenderTickEvent extends Event {
	
	@Override
	public void callListener(EventListener l) {
		l.onRenderTick(this);
	}
	
}
