package de.stylextv.maple.cache.block.matchers;

import de.stylextv.maple.cache.block.BlockMatcher;
import de.stylextv.maple.cache.block.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.WallBlock;

public class BlockadeBlockMatcher extends BlockMatcher {
	
	@Override
	public BlockType match(BlockState state, BlockState above, BlockState below) {
		Block block = below.getBlock();
		
		boolean aboveFence = block instanceof FenceBlock;
		boolean aboveWall = block instanceof WallBlock;
		
		if(aboveFence || aboveWall) return BlockType.UNBREAKABLE;
		
		return null;
	}
	
}
