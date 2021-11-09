package de.stylextv.maple.cache.block.matchers;

import de.stylextv.maple.cache.block.BlockMatcher;
import de.stylextv.maple.cache.block.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class DangerBlockMatcher extends BlockMatcher {
	
	private static final Block[] BLOCKS = new Block[] {
			Blocks.LAVA,
			Blocks.FIRE,
			Blocks.SOUL_FIRE,
			Blocks.CAMPFIRE,
			Blocks.SOUL_CAMPFIRE,
			Blocks.CACTUS,
			Blocks.SWEET_BERRY_BUSH,
			Blocks.POWDER_SNOW,
			Blocks.BUBBLE_COLUMN
	};
	
	@Override
	public BlockType match(Block block) {
		for(Block b : BLOCKS) {
			if(block.equals(b)) return BlockType.DANGER;
		}
		
		return null;
	}
	
}
