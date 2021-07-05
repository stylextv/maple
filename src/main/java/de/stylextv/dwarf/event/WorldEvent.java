package de.stylextv.dwarf.event;

import de.stylextv.dwarf.cache.WorldCache;
import de.stylextv.dwarf.input.PlayerContext;
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
