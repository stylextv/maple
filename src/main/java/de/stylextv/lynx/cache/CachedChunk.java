package de.stylextv.lynx.cache;

import java.util.BitSet;

import de.stylextv.lynx.context.WorldContext;
import net.minecraft.block.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class CachedChunk {
	
	private static final int BIT_AMOUNT = 16 * 16 * 256 * 2;
	
	private CachedRegion region;
	
	private int x;
	private int z;
	
	private BitSet bitSet;
	
	public CachedChunk(CachedRegion r, int x, int z) {
		this(r, x, z, new BitSet(BIT_AMOUNT));
	}
	
	public CachedChunk(CachedRegion r, int x, int z, BitSet bitSet) {
		this.region = r;
		this.x = x;
		this.z = z;
		
		this.bitSet = bitSet;
	}
	
	public boolean update() {
		ClientWorld w = WorldContext.world();
		
		if(w == null) return false;
		
		Chunk c = w.getChunk(x, z);
		
		if(c == null || c.isEmpty()) return false;
		
		int index = 0;
		
		for(int y = 0; y < 256; y++) {
			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					
					BlockState state = c.getBlockState(new BlockPos(x, y, z));
					BlockState below = c.getBlockState(new BlockPos(x, y - 1, z));
					BlockState above = c.getBlockState(new BlockPos(x, y + 1, z));
					
					int i = BlockType.fromBlocks(state, below, above).getID();
					
					for(int j = 0; j < 2; j++) {
						boolean b = (i & 1) != 0;
						
						int k = index * 2 + j;
						
						boolean b2 = bitSet.get(k);
						
						if(b != b2) {
							
							region.setModified(true);
							
							bitSet.set(k, b);
						}
						
						i >>= 1;
					}
					
					index++;
				}
			}
		}
		
		return true;
	}
	
	public boolean isInView() {
		return WorldContext.isChunkInView(x, z);
	}
	
	public BlockType getBlockType(int x, int y, int z) {
		x -= this.x * 16;
		z -= this.z * 16;
		
		int index = y * (16 * 16) + x * 16 + z;
		
		int i = 0;
		
		for(int j = 0; j < 2; j++) {
			boolean b = bitSet.get(index * 2 + j);
			
			if(b) i |= 1 << j;
		}
		
		return BlockType.fromID(i);
	}
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
	public BitSet getBitSet() {
		return bitSet;
	}
	
}
