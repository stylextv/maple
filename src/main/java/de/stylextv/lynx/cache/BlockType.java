package de.stylextv.lynx.cache;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

public enum BlockType {
	
	AIR(0, true), SOLID(1), WATER(2, true), DANGER(3), VOID(-1, true), UNBREAKABLE(-2, false, false);
	
	private static final Block[] DANGER_BLOCKS = new Block[] {
			Blocks.LAVA,
			Blocks.FIRE,
			Blocks.SOUL_FIRE,
			Blocks.CAMPFIRE,
			Blocks.SOUL_CAMPFIRE,
			Blocks.SWEET_BERRY_BUSH,
			Blocks.CACTUS
	};
	
	private int id;
	
	private boolean passable;
	
	private boolean breakable;
	
	private BlockType(int id) {
		this(id, false);
	}
	
	private BlockType(int id, boolean passable) {
		this(id, passable, !passable);
	}
	
	private BlockType(int id, boolean passable, boolean breakable) {
		this.id = id;
		this.passable = passable;
		this.breakable = breakable;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public boolean isBreakable() {
		return breakable;
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
		
		boolean aboveMagma = blockBelow.equals(Blocks.MAGMA_BLOCK);
		
		if(isWater || inWater) {
			return aboveMagma ? DANGER : WATER;
		}
		
		boolean isSolid = state.getMaterial().blocksMotion();
		
		if(isSolid) return SOLID; 
		
		return aboveMagma ? DANGER : AIR;
	}
	
	public static BlockType fromID(int id) {
		return values()[id];
	}
	
}
