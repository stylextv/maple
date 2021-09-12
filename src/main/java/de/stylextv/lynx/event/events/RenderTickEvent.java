package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;

public class RenderTickEvent extends Event {
	
	@Override
	public void callListener(EventListener l) {
		l.onRenderTick(this);
	}
	
}
