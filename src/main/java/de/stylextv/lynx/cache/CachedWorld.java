package de.stylextv.lynx.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.io.FileSystem;
import de.stylextv.lynx.util.AsyncUtil;
import de.stylextv.lynx.util.ThreadInfo;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class CachedWorld {
	
	private static final File SAVE_FOLDER = new File(FileSystem.ROOT_FOLDER, "cache");
	
	private String name;
	
	private Long2ObjectMap<CachedRegion> regionMap;
	
	private ThreadInfo collectThread;
	private ThreadInfo saveThread;
	
	public CachedWorld(String name) {
		this.name = name;
		
		this.regionMap = new Long2ObjectOpenHashMap<>(16, 0.75f);
	}
	
	public void enter() {
		exit();
		
		collectThread = AsyncUtil.loopAsync(() -> collectChunks(), 500);
		saveThread = AsyncUtil.loopAsync(() -> saveChanges(), () -> clear(), 30000, 600000);
	}
	
	public void exit() {
		if(collectThread != null) collectThread.kill();
		if(saveThread != null) saveThread.kill();
	}
	
	private synchronized void clear() {
		saveChanges();
		
		regionMap.clear();
	}
	
	private synchronized void collectChunks() {
		if(!WorldContext.isIngame()) return;
		
		BlockPos p = PlayerContext.player().blockPosition();
		
		ChunkPos pos = new ChunkPos(p);
		
		int dis = WorldContext.getViewDistance();
		
		for(int x = -dis; x <= dis; x++) {
			for(int z = -dis; z <= dis; z++) {
				
				int cx = pos.x + x;
				int cz = pos.z + z;
				
				if(WorldContext.isChunkInView(cx, cz)) {
					
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
	
	public BlockType getBlockType(BlockPos pos) {
		int y = pos.getY();
		
		if(y < 0 || y > 255) return BlockType.VOID;
		
		ChunkPos p = new ChunkPos(pos);
		
		CachedChunk chunk = getChunk(p.x, p.z);
		
		if(chunk == null) return BlockType.VOID;
		
		int x = pos.getX();
		int z = pos.getZ();
		
		return chunk.getBlockType(x, y, z);
	}
	
	public CachedChunk getChunk(int cx, int cz) {
		CachedRegion r = getRegion(cx, cz);
		
		return r.getChunk(cx, cz);
	}
	
	private synchronized CachedRegion getRegion(int cx, int cz) {
		int rx = CachedRegion.chunkToRegionPos(cx);
		int rz = CachedRegion.chunkToRegionPos(cz);
		
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
