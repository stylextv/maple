package de.stylextv.dwarf.cache;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

public enum BlockType {
	
	AIR(0), SOLID(1), WATER(2), AVOID(3);
	
	private int id;
	
	private BlockType(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public static BlockType fromBlocks(BlockState center, BlockState bottom, BlockState top) {
		boolean isWater = center.getBlock().equals(Blocks.WATER);
		
		Fluid fluidType = center.getFluidState().getType();
		
		boolean inWater = fluidType.equals(Fluids.WATER) || fluidType.equals(Fluids.FLOWING_WATER);
		
		if(isWater || inWater) return WATER;
		
		boolean isSolid = center.getMaterial().blocksMotion();
		
		if(isSolid) return SOLID;
		return AIR;
	}
	
	public static BlockType fromID(int id) {
		return values()[id];
	}
	
}
