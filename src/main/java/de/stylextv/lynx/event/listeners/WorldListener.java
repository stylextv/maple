package de.stylextv.lynx.event.listeners;

import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.event.EventListener;
import de.stylextv.lynx.event.events.WorldEvent;

public class WorldListener implements EventListener {
	
	@Override
	public void onWorldLoad(WorldEvent event) {
		if(!event.getWorld().isClient()) return;
		
		CacheManager.enterWorld();
	}
	
	@Override
	public void onWorldUnload(WorldEvent event) {
		if(!event.getWorld().isClient()) return;
		
		CacheManager.exitWorld();
	}
	
}
