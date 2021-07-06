package de.stylextv.lynx.cache;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

public enum BlockType {
	
	AIR(0), SOLID(1), WATER(2), DANGER(3), VOID(-1);
	
	private static final Block[] DANGER_BLOCKS = new Block[] {
			Blocks.LAVA,
			Blocks.FIRE,
			Blocks.SOUL_FIRE,
			Blocks.MAGMA_BLOCK,
			Blocks.CAMPFIRE,
			Blocks.SOUL_CAMPFIRE,
			Blocks.SWEET_BERRY_BUSH,
			Blocks.CACTUS
	};
	
	private int id;
	
	private BlockType(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public static BlockType fromBlocks(BlockState state, BlockState below, BlockState above) {
		Block block = state.getBlock();
		
		for(Block b : DANGER_BLOCKS) {
			if(block.equals(b)) return DANGER;
		}
		
		if(block.equals(Blocks.LILY_PAD)) return AIR;
		
		Block blockBelow = below.getBlock();
		
		boolean aboveFence = blockBelow instanceof FenceBlock || blockBelow instanceof FenceGateBlock;
		
		if(aboveFence || above.getBlock().equals(Blocks.LILY_PAD)) {
			return SOLID;
		}
		
		boolean isWater = block.equals(Blocks.WATER);
		
		Fluid fluidType = state.getFluidState().getType();
		
		boolean inWater = fluidType.equals(Fluids.WATER) || fluidType.equals(Fluids.FLOWING_WATER);
		
		if(isWater || inWater) return WATER;
		
		boolean isSolid = state.getMaterial().blocksMotion();
		
		return isSolid ? SOLID : AIR;
	}
	
	public static BlockType fromID(int id) {
		return values()[id];
	}
	
}
