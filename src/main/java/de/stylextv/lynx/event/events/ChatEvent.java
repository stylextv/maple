package de.stylextv.lynx.event.events;

import de.stylextv.lynx.command.CommandManager;
import de.stylextv.lynx.context.GameContext;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChatEvent {
	
	@SubscribeEvent
	public void onChat(ClientChatEvent event) {
		String msg = event.getOriginalMessage();
		
		if(CommandManager.isCommandMessage(msg)) {
			event.setCanceled(true);
			
			GameContext.chatHud().addToMessageHistory(msg);
			
			CommandManager.parseMessage(msg);
		}
	}
	
}
