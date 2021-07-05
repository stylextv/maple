package de.stylextv.lynx.event;

import de.stylextv.lynx.cache.WorldCache;
import de.stylextv.lynx.input.PlayerContext;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldEvent {
	
	@SubscribeEvent
    public void onLeave(EntityLeaveWorldEvent event) {
    	if(event.getEntity().equals(PlayerContext.player())) {
    		WorldCache.clear();
    	}
    }
    
}
