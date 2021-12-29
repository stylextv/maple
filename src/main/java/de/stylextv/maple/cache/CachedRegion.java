package de.stylextv.maple.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.stylextv.maple.cache.io.file.CachedRegionFile;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.util.world.CoordUtil;
import net.minecraft.util.math.ChunkPos;

public class CachedRegion {
	
	private static final int SIZE = 32;
	
	private CachedWorld world;
	
	private int x;
	private int z;
	
	private int chunkX;
	private int chunkZ;
	
	private CachedChunk[][] chunks = new CachedChunk[SIZE][SIZE];
	
	private boolean modified;
	
	public CachedRegion(CachedWorld world, int x, int z) {
		this.world = world;
		this.x = x;
		this.z = z;
		
		this.chunkX = x * SIZE;
		this.chunkZ = z * SIZE;
	}
	
	public void addChunk(CachedChunk c) {
		int x = c.getX() - chunkX;
		int z = c.getZ() - chunkZ;
		
		chunks[x][z] = c;
		
		c.setRegion(this);
	}
	
	public void saveChanges() {
		if(!modified) return;
		
		File f = getSaveFile();
		
		CachedRegionFile file = new CachedRegionFile(f);
		
		file.writeRegion(this);
		
		file.close();
	}
	
	public boolean isInView() {
		if(!WorldContext.isInWorld()) return false;
		
		ChunkPos pos = PlayerContext.chunkPosition();
		
		int rx = CoordUtil.chunkToRegionPos(pos.x);
		int rz = CoordUtil.chunkToRegionPos(pos.z);
		
		int disX = Math.abs(x - rx);
		int disZ = Math.abs(z - rz);
		
		return disX < 2 && disZ < 2;
	}
	
	public List<CachedChunk> chunks() {
		List<CachedChunk> list = new ArrayList<>();
		
		for(int x = 0; x < SIZE; x++) {
			for(int z = 0; z < SIZE; z++) {
				
				CachedChunk chunk = chunks[x][z];
				
				if(chunk != null) list.add(chunk);
			}
		}
		
		return list;
	}
	
	public CachedChunk getChunk(int cx, int cz) {
		int x = cx - chunkX;
		int z = cz - chunkZ;
		
		return chunks[x][z];
	}
	
	public File getSaveFile() {
		return world.getSaveFile(this);
	}
	
	public long getHash() {
		return posAsLong(x, z);
	}
	
	public CachedWorld getWorld() {
		return world;
	}
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
	public boolean wasModified() {
		return modified;
	}
	
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	public static CachedRegion loadFromDisk(CachedWorld world, int x, int z) {
		CachedRegion r = new CachedRegion(world, x, z);
		
		File f = r.getSaveFile();
		
		CachedRegionFile file = new CachedRegionFile(f);
		
		if(file.exists()) file.readRegion(r);
		
		file.close();
		
		return r;
	}
	
	public static long posAsLong(int x, int z) {
		return CoordUtil.posAsLong(x, 0, z);
	}
	
}
