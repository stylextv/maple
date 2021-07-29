package de.stylextv.lynx.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.io.FileAccess;
import de.stylextv.lynx.io.FileSystem;
import de.stylextv.lynx.util.CoordUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public class CachedRegion {
	
	private static final int SIZE = 32;
	
	private CachedLevel world;
	
	private int x;
	private int z;
	
	private int chunkX;
	private int chunkZ;
	
	private CachedChunk[][] chunks = new CachedChunk[SIZE][SIZE];
	
	private boolean modified;
	
	public CachedRegion(CachedLevel world, int x, int z) {
		this.world = world;
		this.x = x;
		this.z = z;
		
		this.chunkX = x * SIZE;
		this.chunkZ = z * SIZE;
	}
	
	public void storeChunk(CachedChunk chunk) {
		int x = chunk.getX() - chunkX;
		int z = chunk.getZ() - chunkZ;
		
		chunks[x][z] = chunk;
	}
	
	public void update() {
		for(CachedChunk chunk : chunks()) {
			chunk.update();
		}
	}
	
	public void saveChanges() {
		if(!modified) return;
		
		File f = getSaveFile();
		
		FileAccess access = FileSystem.openFile(f);
		
		FileSystem.writeRegion(this, access);
		
		access.close();
	}
	
	public CachedChunk getChunk(int cx, int cz) {
		int x = cx - chunkX;
		int z = cz - chunkZ;
		
		return chunks[x][z];
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
	
	public boolean isInView() {
		if(!LevelContext.isInLevel()) return false;
		
		ChunkPos pos = PlayerContext.chunkPosition();
		
		int rx = CoordUtil.chunkToRegionPos(pos.x);
		int rz = CoordUtil.chunkToRegionPos(pos.z);
		
		int disX = Math.abs(x - rx);
		int disZ = Math.abs(z - rz);
		
		return disX < 2 && disZ < 2;
	}
	
	public File getSaveFile() {
		return world.getSaveFile(this);
	}
	
	public long getHash() {
		return posAsLong(x, z);
	}
	
	public CachedLevel getWorld() {
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
	
	public static CachedRegion loadFromDisk(CachedLevel world, int x, int z) {
		CachedRegion r = new CachedRegion(world, x, z);
		
		File f = world.getSaveFile(r);
		
		FileAccess access = FileSystem.openFile(f);
		
		r = FileSystem.readRegion(r, access);
		
		access.close();
		
		return r;
	}
	
	public static long posAsLong(int x, int z) {
		return BlockPos.asLong(x, 0, z);
	}
	
}
