package de.stylextv.lynx.event;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
	
	private static List<EventListener> listeners = new ArrayList<>();
	
	public static void registerListener(EventListener l) {
		listeners.add(l);
	}
	
	public static void onEvent(Event event) {
		listeners.forEach(l -> event.callListener(l));
	}
	
}
