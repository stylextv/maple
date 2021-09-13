package de.stylextv.lynx.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.io.FileSystem;
import de.stylextv.lynx.util.async.AsyncUtil;
import de.stylextv.lynx.util.async.TaskInfo;
import de.stylextv.lynx.util.world.CoordUtil;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public class CachedWorld {
	
	private static final File SAVE_FOLDER = new File(FileSystem.ROOT_FOLDER, "cache");
	
	private String name;
	
	private Long2ObjectMap<CachedRegion> regionMap;
	
	private TaskInfo saveTask;
	
	public CachedWorld(String name) {
		this.name = name;
		
		this.regionMap = new Long2ObjectOpenHashMap<>(16, 0.75f);
	}
	
	public void enter() {
		exit();
		
		saveTask = AsyncUtil.loopAsync(() -> saveChanges(), () -> clear(), 30000, 600000);
	}
	
	public void exit() {
		if(saveTask != null) saveTask.kill();
	}
	
	private synchronized void clear() {
		saveChanges();
		
		regionMap.clear();
	}
	
	public void collectChunk(WorldChunk chunk) {
		ChunkPos pos = chunk.getPos();
		
		int x = pos.x;
		int z = pos.z;
		
		CachedChunk c = getChunk(x, z);
		
		if(c == null) {
			
			CachedRegion r = getRegion(x, z);
			
			c = new CachedChunk(r, x, z);
			
			r.storeChunk(c);
		}
		
		c.load(chunk);
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
