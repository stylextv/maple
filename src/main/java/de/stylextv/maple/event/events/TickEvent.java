package de.stylextv.maple.event.events;

import de.stylextv.maple.event.Event;
import de.stylextv.maple.event.EventListener;

public class TickEvent extends Event {
	
	private Type type;
	
	public TickEvent(Type type) {
		this.type = type;
	}
	
	@Override
	public void callListener(EventListener l) {
		if(type == Type.CLIENT) {
			
			l.onClientTick(this);
			
			return;
		}
		
		if(type == Type.RENDER) {
			
			l.onRenderTick(this);
		}
	}
	
	public Type getType() {
		return type;
	}
	
	public enum Type {
		
		CLIENT, RENDER;
		
	}
	
}
