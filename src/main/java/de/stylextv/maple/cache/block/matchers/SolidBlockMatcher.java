package de.stylextv.maple.cache.block.matchers;

import de.stylextv.maple.cache.block.BlockMatcher;
import de.stylextv.maple.cache.block.BlockType;
import de.stylextv.maple.context.WorldContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public class SolidBlockMatcher extends BlockMatcher {
	
	@Override
	public BlockType match(BlockState state, BlockState above, BlockState below, BlockPos pos) {
		boolean isSolid = state.getMaterial().blocksMovement();
		
		if(state.getBlock().equals(Blocks.FLOWERING_AZALEA)) {
			
			ClientWorld world = WorldContext.world();
			
			System.out.println(state.getCollisionShape(world, pos));
		}
		
		if(isSolid) return BlockType.SOLID;
		
		return null;
	}
	
}
