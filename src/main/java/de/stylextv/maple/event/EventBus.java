package de.stylextv.maple.event;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.maple.context.ClientContext;

public class EventBus {
	
	private static List<EventListener> listeners = new ArrayList<>();
	
	public static void registerListener(EventListener l) {
		listeners.add(l);
	}
	
	public static void onEvent(Event event) {
		if(!ClientContext.isClientSide()) return;
		
		listeners.forEach(l -> event.callListener(l));
	}
	
}
