package de.stylextv.lynx.event;

import de.stylextv.lynx.command.CommandManager;
import de.stylextv.lynx.input.PlayerContext;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChatEvent {
	
	@SubscribeEvent
	public void onChat(ClientChatEvent event) {
		String msg = event.getOriginalMessage();
		
		if(CommandManager.isCommandMessage(msg)) {
			event.setCanceled(true);
			
			PlayerContext.ingameGui().getChat().addRecentChat(msg);
			
			CommandManager.parseMessage(msg);
		}
	}
	
}
