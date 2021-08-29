package de.stylextv.lynx.event;

import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.util.async.AsyncUtil;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LevelEvent {
	
	private static final int LOAD_DELAY = 1250;
	
	@SubscribeEvent
	public void onLoad(WorldEvent.Load event) {
		if(!event.getWorld().isClientSide()) return;
		
		AsyncUtil.runLaterAsync(() -> CacheManager.enterLevel(), LOAD_DELAY);
	}
	
	@SubscribeEvent
	public void onUnload(WorldEvent.Unload event) {
		if(!event.getWorld().isClientSide()) return;
		
		CacheManager.exitLevel();
	}
	
}
