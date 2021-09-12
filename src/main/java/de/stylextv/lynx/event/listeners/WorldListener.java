package de.stylextv.lynx.event.listeners;

import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.event.EventListener;
import de.stylextv.lynx.event.events.WorldLoadEvent;
import de.stylextv.lynx.event.events.WorldUnloadEvent;

public class WorldListener implements EventListener {
	
	@Override
	public void onWorldLoad(WorldLoadEvent event) {
		if(!event.getWorld().isClient()) return;
		
		System.out.println("load: " + event.getWorld());
		
		CacheManager.enterWorld();
	}
	
	@Override
	public void onWorldUnload(WorldUnloadEvent event) {
		if(!event.getWorld().isClient()) return;
		
		System.out.println("unload: " + event.getWorld());
		
		CacheManager.exitWorld();
	}
	
}
