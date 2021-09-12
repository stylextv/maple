package de.stylextv.lynx.event.listeners;

import de.stylextv.lynx.command.CommandManager;
import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.event.EventListener;
import de.stylextv.lynx.event.events.ClientChatEvent;

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
