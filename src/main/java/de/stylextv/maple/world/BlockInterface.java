package de.stylextv.maple.world;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.context.WorldContext;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockInterface {
	
	public static BlockState getState(int x, int y, int z) {
		return getState(new BlockPos(x, y, z));
	}
	
	public static BlockState getState(BlockPos pos) {
		boolean loaded = WorldBorderInterface.isInside(pos) && WorldContext.isPosFullyLoaded(pos);
		
		if(loaded) return WorldContext.getBlockState(pos);
		
		BlockType type = CacheManager.getBlockType(pos);
		
		return type.getState();
	}
	
}
