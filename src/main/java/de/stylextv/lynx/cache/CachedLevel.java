package de.stylextv.lynx.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.io.FileSystem;
import de.stylextv.lynx.util.CoordUtil;
import de.stylextv.lynx.util.async.AsyncUtil;
import de.stylextv.lynx.util.async.TaskInfo;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import net.minecraft.world.level.ChunkPos;

public class CachedLevel {
	
	private static final File SAVE_FOLDER = new File(FileSystem.ROOT_FOLDER, "cache");
	
	private String name;
	
	private Long2ObjectMap<CachedRegion> regionMap;
	
	private TaskInfo collectTask;
	private TaskInfo saveTask;
	
	public CachedLevel(String name) {
		this.name = name;
		
		this.regionMap = new Long2ObjectOpenHashMap<>(16, 0.75f);
	}
	
	public void enter() {
		exit();
		
		collectTask = AsyncUtil.loopAsync(() -> collectChunks(), 500);
		saveTask = AsyncUtil.loopAsync(() -> saveChanges(), () -> clear(), 30000, 600000);
	}
	
	public void exit() {
		if(collectTask != null) collectTask.kill();
		if(saveTask != null) saveTask.kill();
	}
	
	private synchronized void clear() {
		saveChanges();
		
		regionMap.clear();
	}
	
	private synchronized void collectChunks() {
		if(!GameContext.isIngame()) return;
		
		ChunkPos pos = PlayerContext.chunkPosition();
		
		int dis = LevelContext.getViewDistance();
		
		for(int x = -dis; x <= dis; x++) {
			for(int z = -dis; z <= dis; z++) {
				
				int cx = pos.x + x;
				int cz = pos.z + z;
				
				if(LevelContext.isChunkInView(cx, cz)) {
					
					if(getChunk(cx, cz) == null) {
						
						CachedRegion r = getRegion(cx, cz);
						
						CachedChunk chunk = new CachedChunk(r, cx, cz);
						
						if(chunk.update()) storeChunk(chunk);
					}
				}
			}
		}
		
		for(CachedRegion r : regions()) {
			r.update();
		}
	}
	
	private void storeChunk(CachedChunk chunk) {
		int cx = chunk.getX();
		int cz = chunk.getZ();
		
		CachedRegion r = getRegion(cx, cz);
		
		r.storeChunk(chunk);
	}
	
	private synchronized void saveChanges() {
		for(CachedRegion r : regions()) {
			
			r.saveChanges();
			
			if(!r.isInView()) {
				
				int x = r.getX();
				int z = r.getZ();
				
				long hash = CachedRegion.posAsLong(x, z);
				
				regionMap.remove(hash);
			}
		}
	}
	
	public BlockType getBlockType(int x, int y, int z) {
		if(y < 0 || y > 255) return BlockType.VOID;
		
		int cx = CoordUtil.blockToChunkPos(x);
		int cz = CoordUtil.blockToChunkPos(z);
		
		CachedChunk chunk = getChunk(cx, cz);
		
		if(chunk == null) return BlockType.UNLOADED;
		
		return chunk.getBlockType(x, y, z);
	}
	
	public CachedChunk getChunk(int cx, int cz) {
		CachedRegion r = getRegion(cx, cz);
		
		return r.getChunk(cx, cz);
	}
	
	private CachedRegion getRegion(int cx, int cz) {
		int rx = CoordUtil.chunkToRegionPos(cx);
		int rz = CoordUtil.chunkToRegionPos(cz);
		
		long hash = CachedRegion.posAsLong(rx, rz);
		
		CachedRegion r = regionMap.get(hash);
		
		if(r == null) {
			
			r = CachedRegion.loadFromDisk(this, rx, rz);
			
			regionMap.put(hash, r);
		}
		
		return r;
	}
	
	private synchronized List<CachedRegion> regions() {
		ObjectCollection<CachedRegion> values = regionMap.values();
		
		return new ArrayList<>(values);
	}
	
	public File getSaveFile(CachedRegion r) {
		File f = getSaveFolder();
		
		int x = r.getX();
		int z = r.getZ();
		
		String name = String.format("region_%s,%s.dat", x, z);
		
		return new File(f, name);
	}
	
	public File getSaveFolder() {
		return new File(SAVE_FOLDER, name);
	}
	
	public String getName() {
		return name;
	}
	
}
