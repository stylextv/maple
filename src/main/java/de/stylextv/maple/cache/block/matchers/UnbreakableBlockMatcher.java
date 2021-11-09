package de.stylextv.maple.cache.block.matchers;

import de.stylextv.maple.cache.block.BlockMatcher;
import de.stylextv.maple.cache.block.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.InfestedBlock;

public class UnbreakableBlockMatcher extends BlockMatcher {
	
	@Override
	public BlockType match(Block block) {
		boolean isUnbreakable = block.getHardness() < 0;
		
		if(isUnbreakable || block instanceof InfestedBlock) return BlockType.UNBREAKABLE;
		
		return null;
	}
	
}
