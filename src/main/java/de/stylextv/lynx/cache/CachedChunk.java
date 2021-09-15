package de.stylextv.lynx.cache;

import java.util.BitSet;

import de.stylextv.lynx.util.world.CoordUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

public class CachedChunk {
	
	private static final int BIT_AMOUNT = 16 * 16 * 256 * 2;
	
	private CachedRegion region;
	
	private int x;
	private int z;
	
	private WorldChunk chunk;
	
	private BitSet bitSet;
	
	private boolean loaded = true;
	
	public CachedChunk(CachedRegion r, int x, int z) {
		this(r, x, z, new BitSet(BIT_AMOUNT));
		
		this.loaded = false;
	}
	
	public CachedChunk(CachedRegion r, int x, int z, BitSet bitSet) {
		this.region = r;
		this.x = x;
		this.z = z;
		
		this.bitSet = bitSet;
	}
	
	public void load(WorldChunk c) {
		chunk = c;
		
		for(int y = 0; y < 256; y++) {
			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					
					updatePos(x, y, z);
				}
			}
		}
		
		loaded = true;
	}
	
	public void updatePos(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		
		updatePos(pos);
	}
	
	public void updatePos(BlockPos pos) {
		if(chunk == null) return;
		
		BlockState state = chunk.getBlockState(pos);
		BlockState below = chunk.getBlockState(pos.down());
		BlockState above = chunk.getBlockState(pos.up());
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		x = CoordUtil.posInChunk(x);
		z = CoordUtil.posInChunk(z);
		
		int index = getDataIndex(x, y, z);
		
		BlockType type = BlockType.fromBlocks(state, below, above);
		
		boolean[] bits = type.getBits();
		
		for(int i = 0; i < 2; i++) {
			boolean b = bits[i];
			
			int k = index + i;
			
			boolean b2 = bitSet.get(k);
			
			if(b != b2) {
				
				bitSet.set(k, b);
				
				region.setModified(true);
			}
		}
	}
	
	public BlockType getBlockType(int x, int y, int z) {
		x -= this.x * 16;
		z -= this.z * 16;
		
		int index = getDataIndex(x, y, z);
		
		boolean b1 = bitSet.get(index);
		boolean b2 = bitSet.get(index + 1);
		
		return BlockType.fromBits(b1, b2);
	}
	
	private int getDataIndex(int x, int y, int z) {
		return (y << 9) | (x << 5) | (z << 1);
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
	
	public boolean isLoaded() {
		return loaded;
	}
	
}
