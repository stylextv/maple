package de.stylextv.maple.cache.block.matchers;

import de.stylextv.maple.cache.block.BlockMatcher;
import de.stylextv.maple.cache.block.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

public class FluidBlockMatcher extends BlockMatcher {
	
	@Override
	public BlockType match(BlockState state, BlockState above, BlockState below) {
		Fluid fluid = state.getFluidState().getFluid();
		
		if(fluid.equals(Fluids.FLOWING_WATER)) return BlockType.DANGER;
		
		Block block = below.getBlock();
		
		boolean aboveMagma = block.equals(Blocks.MAGMA_BLOCK);
		
		if(aboveMagma) return BlockType.DANGER;
		
		boolean isWater = fluid.equals(Fluids.WATER);
		
		if(isWater) return BlockType.WATER;
		
		return null;
	}
	
}
