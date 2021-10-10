package de.stylextv.maple.event.events;

import de.stylextv.maple.event.EventListener;
import de.stylextv.maple.event.events.types.Cancelable;

public class ClientChatEvent extends Cancelable {
	
	private String message;
	
	public ClientChatEvent(String message) {
		this.message = message;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onClientChat(this);
	}
	
	public String getOriginalMessage() {
		return message;
	}
	
}
