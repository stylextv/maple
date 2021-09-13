package de.stylextv.lynx.event.listeners;

import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.cache.CachedWorld;
import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.event.EventListener;
import de.stylextv.lynx.event.events.ChunkEvent;
import de.stylextv.lynx.event.events.WorldEvent;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.chunk.WorldChunk;

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
	
	@Override
	public void onChunkLoad(ChunkEvent event) {
		int x = event.getX();
		int z = event.getZ();
		
		ClientWorld world = WorldContext.world();
		
		WorldChunk chunk = world.getChunk(x, z);
		
		CachedWorld w = CacheManager.getWorld();
		
		w.collectChunk(chunk);
	}
	
	// TODO listen for block update events and inform ChunkManager.getWorld()
	
}
