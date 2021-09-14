package de.stylextv.lynx.event.listeners;

import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.cache.CachedWorld;
import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.event.EventListener;
import de.stylextv.lynx.event.events.BlockUpdateEvent;
import de.stylextv.lynx.event.events.ChunkEvent;
import de.stylextv.lynx.event.events.WorldEvent;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkStatus;
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
	public void onChunkData(ChunkEvent event) {
		int x = event.getX();
		int z = event.getZ();
		
		ClientWorld world = WorldContext.world();
		
		WorldChunk chunk = world.getChunk(x, z);
		
		if(chunk.isEmpty() || chunk.getStatus() != ChunkStatus.FULL) return;
		
		CachedWorld w = CacheManager.getWorld();
		
		w.addChunk(chunk);
	}
	
	@Override
	public void onBlockUpdate(BlockUpdateEvent event) {
		BlockPos pos = event.getPos();
		
		CachedWorld w = CacheManager.getWorld();
		
		w.updatePos(pos);
	}
	
}
