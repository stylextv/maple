package de.stylextv.maple.event.listeners;

import de.stylextv.maple.command.CommandManager;
import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.event.EventListener;
import de.stylextv.maple.event.events.ClientChatEvent;

public class ChatListener implements EventListener {
	
	@Override
	public void onClientChat(ClientChatEvent event) {
		String msg = event.getOriginalMessage();
		
		if(CommandManager.isCommandMessage(msg)) {
			event.setCanceled(true);
			
			GameContext.chatHud().addToMessageHistory(msg);
			
			CommandManager.parseMessage(msg);
		}
	}
	
}
