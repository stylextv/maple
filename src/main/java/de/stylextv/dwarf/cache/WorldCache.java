package de.stylextv.dwarf.cache;

import de.stylextv.dwarf.input.PlayerContext;
import de.stylextv.dwarf.util.AsyncUtil;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class WorldCache {
	
	private static final int VIEW_DISTANCE = 12;
	
	private static Long2ObjectOpenHashMap<ChunkCache> chunkMap;
	
	private static boolean running = true;
	
	private static boolean clear;
	
	public static void init() {
		chunkMap = new Long2ObjectOpenHashMap<>(512, 0.75f);
		
		AsyncUtil.runAsync(() -> run());
	}
	
	private static void run() {
		while(running) {
			update();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void shutdown() {
		running = false;
	}
	
	public static void clear() {
		clear = true;
	}
	
	public static void update() {
		if(clear) {
			chunkMap.clear();
			
			clear = false;
		}
		
		if(!PlayerContext.isIngame()) return;
		
		BlockPos p = PlayerContext.player().blockPosition();
		
		ChunkPos pos = new ChunkPos(p);
		
		for(int x = -VIEW_DISTANCE; x <= VIEW_DISTANCE; x++) {
			for(int z = -VIEW_DISTANCE; z <= VIEW_DISTANCE; z++) {
				
				int wx = pos.x + x;
				int wz = pos.z + z;
				
				if(isInView(wx, wz)) {
					
					long hash = ChunkCache.posAsLong(wx, wz);
					
					if(chunkMap.get(hash) == null) {
						ChunkCache cache = new ChunkCache(wx, wz);
						
						if(cache.update()) chunkMap.put(hash, cache);
					}
				}
			}
		}
		
		LongIterator iter = chunkMap.keySet().iterator();
		
		while(iter.hasNext()) {
			long hash = iter.nextLong();
			
			ChunkCache c = chunkMap.get(hash);
			
			if(c.isInView()) c.update();
			else iter.remove();
		}
	}
	
	public static BlockType getBlockType(int x, int y, int z) {
		return getBlockType(new BlockPos(x, y, z));
	}
	
	public static BlockType getBlockType(BlockPos pos) {
		ChunkPos p = new ChunkPos(pos);
		
		ChunkCache chunk = getChunk(p.x, p.z);
		
		if(chunk == null) return BlockType.AIR;
		
		int rx = pos.getX() - p.x * 16;
		int rz = pos.getZ() - p.z * 16;
		
		return chunk.getBlockType(rx, pos.getY(), rz);
	}
	
	public static ChunkCache getChunk(int cx, int cz) {
		return chunkMap.get(ChunkCache.posAsLong(cx, cz));
	}
	
	public static boolean isInView(int cx, int cz) {
		if(!PlayerContext.isIngame()) return false;
		
		BlockPos p = PlayerContext.player().blockPosition();
		
		ChunkPos pos = new ChunkPos(p);
		
		int dis = Math.abs(pos.x - cx) + Math.abs(pos.z - cz);
		
		return dis < VIEW_DISTANCE;
	}
	
}
