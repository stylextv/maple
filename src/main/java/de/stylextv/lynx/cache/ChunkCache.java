package de.stylextv.lynx.cache;

import java.util.BitSet;

import de.stylextv.lynx.input.PlayerContext;
import net.minecraft.block.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class ChunkCache {
	
	private int x;
	private int z;
	
	private BitSet bitSet = new BitSet(16 * 16 * 256 * 2);
	
	public ChunkCache(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	public boolean update() {
		ClientWorld w = PlayerContext.world();
		
		if(w == null) return false;
		
		Chunk c = w.getChunk(x, z);
		
		if(c == null || c.isEmpty()) return false;
		
		int index = 0;
		
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				for(int y = 0; y < 256; y++) {
					
					BlockState state = c.getBlockState(new BlockPos(x, y, z));
					BlockState below = c.getBlockState(new BlockPos(x, y - 1, z));
					BlockState above = c.getBlockState(new BlockPos(x, y + 1, z));
					
					int i = BlockType.fromBlocks(state, below, above).getID();
					
					for(int j = 0; j < 2; j++) {
						boolean b = (i & 1) != 0;
						
						bitSet.set(index * 2 + j, b);
						
						i >>= 1;
					}
					
					index++;
				}
			}
		}
		
		return true;
	}
	
	public boolean isInView() {
		return WorldCache.isInView(x, z);
	}
	
	public BlockType getBlockType(int x, int y, int z) {
		int index = x * (16 * 256) + z * 256 + y;
		
		int i = 0;
		
		for(int j = 0; j < 2; j++) {
			boolean b = bitSet.get(index * 2 + j);
			
			if(b) i |= 1 << j;
		}
		
		return BlockType.fromID(i);
	}
	
	public long getHash() {
		return posAsLong(x, z);
	}
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
	public static long posAsLong(int x, int z) {
		return BlockPos.asLong(x, 0, z);
	}
	
}
