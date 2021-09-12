package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Cancelable;
import de.stylextv.lynx.event.EventListener;

public class ClientChatEvent extends Cancelable {
	
	private String message;
	
	public ClientChatEvent(String s) {
		this.message = s;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onClientChat(this);
	}
	
	public String getOriginalMessage() {
		return message;
	}
	
}
