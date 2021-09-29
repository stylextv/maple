package de.stylextv.lynx.cache;

import java.util.BitSet;

import de.stylextv.lynx.util.world.CoordUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

public class CachedChunk {
	
	private static final int BITS_PER_LAYER = 16 * 16 * 2;
	
	private CachedRegion region;
	
	private int x;
	private int z;
	
	private int height;
	
	private int bottomY;
	
	private WorldChunk chunk;
	
	private BitSet bitSet;
	
	private boolean loaded = true;
	
	public CachedChunk(CachedRegion r, int x, int z, int height, int bottomY) {
		this(r, x, z, height, bottomY, new BitSet(BITS_PER_LAYER * height));
		
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
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					
					updatePos(x, bottomY + y, z);
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
		int index = getDataIndex(x, y, z);
		
		boolean b1 = bitSet.get(index);
		boolean b2 = bitSet.get(index + 1);
		
		return BlockType.fromBits(b1, b2);
	}
	
	private int getDataIndex(int x, int y, int z) {
		x = CoordUtil.posInChunk(x);
		z = CoordUtil.posInChunk(z);
		
		y -= bottomY;
		
		return (y << 9) | (x << 5) | (z << 1);
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
