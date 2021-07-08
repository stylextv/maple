package de.stylextv.lynx.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.io.FileAccess;
import de.stylextv.lynx.io.FileSystem;
import net.minecraft.util.math.BlockPos;

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
		for(int x = 0; x < SIZE; x++) {
			for(int z = 0; z < SIZE; z++) {
				
				int cx = chunkX + x;
				int cz = chunkZ + z;
				
				if(WorldContext.isChunkInView(cx, cz)) return true;
			}
		}
		
		return false;
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
		
		File f = world.getSaveFile(r);
		
		FileAccess access = FileSystem.openFile(f);
		
		r = FileSystem.readRegion(r, access);
		
		access.close();
		
		return r;
	}
	
	public static int chunkToRegionPos(int i) {
		return i >> 5;
	}
	
	public static long posAsLong(int x, int z) {
		return BlockPos.asLong(x, 0, z);
	}
	
}
