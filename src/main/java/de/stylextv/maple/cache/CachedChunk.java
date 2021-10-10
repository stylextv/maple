package de.stylextv.maple.cache;

import java.util.BitSet;

import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.util.world.CoordUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

public class CachedChunk {
	
	private static final int BITS_PER_LAYER = 16 * 16 * 3;
	
	private static final int INVALID_INDEX = -1;
	
	private CachedRegion region;
	
	private int x;
	private int z;
	
	private int height;
	
	private int bottomY;
	
	private WorldChunk chunk;
	
	private BitSet bitSet;
	
	private boolean loaded = true;
	
	public CachedChunk(CachedRegion r, int x, int z) {
		this(r, x, z, 0, 0, null);
		
		this.loaded = false;
	}
	
	public CachedChunk(CachedRegion r, int x, int z, int height, int bottomY, BitSet bitSet) {
		this.region = r;
		this.x = x;
		this.z = z;
		
		this.height = height;
		this.bottomY = bottomY;
		
		this.bitSet = bitSet;
	}
	
	public void load(WorldChunk c) {
		chunk = c;
		
		int height = WorldContext.getHeight();
		
		int bottomY = WorldContext.getBottomY();
		
		updateHeightLimit(height, bottomY);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					
					updatePos(x, bottomY + y, z);
				}
			}
		}
		
		loaded = true;
	}
	
	private void updateHeightLimit(int height, int bottomY) {
		this.bottomY = bottomY;
		
		if(height != this.height) {
			
			this.height = height;
			
			bitSet = new BitSet(BITS_PER_LAYER * height);
		}
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
		
		int index = getDataIndex(x, y, z);
		
		if(index == INVALID_INDEX) return;
		
		BlockType type = BlockType.fromBlocks(state, below, above);
		
		boolean[] bits = type.getBits();
		
		for(int i = 0; i < BlockType.BIT_AMOUNT; i++) {
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
		int index = getDataIndex(x, y, z);
		
		if(index == INVALID_INDEX) return BlockType.AIR;
		
		boolean b1 = bitSet.get(index);
		boolean b2 = bitSet.get(index + 1);
		boolean b3 = bitSet.get(index + 2);
		
		return BlockType.fromBits(b1, b2, b3);
	}
	
	private int getDataIndex(int x, int y, int z) {
		x = CoordUtil.posInChunk(x);
		z = CoordUtil.posInChunk(z);
		
		y -= bottomY;
		
		if(y < 0 || y >= height) return INVALID_INDEX;
		
		int index = (y << 8) | (x << 4) | z;
		
		return index * BlockType.BIT_AMOUNT;
	}
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getBottomY() {
		return bottomY;
	}
	
	public BitSet getBitSet() {
		return bitSet;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
}
