package de.stylextv.lynx.event;

import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.context.PlayerContext;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldEvent {
	
	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if(event.getEntity().equals(PlayerContext.player())) {
			CacheManager.enterLevel();
		}
	}
	
	@SubscribeEvent
	public void onLeave(EntityLeaveWorldEvent event) {
		if(event.getEntity().equals(PlayerContext.player())) {
			CacheManager.exitLevel();
		}
	}
	
}
