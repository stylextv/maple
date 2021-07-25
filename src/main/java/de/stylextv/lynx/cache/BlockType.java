package de.stylextv.lynx.cache;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public enum BlockType {
	
	AIR(0, true), SOLID(1), WATER(2, true), DANGER(3), VOID(-1, true), UNBREAKABLE(-1, false, false);
	
	private static final Block[] DANGER_BLOCKS = new Block[] {
			Blocks.LAVA,
			Blocks.FIRE,
			Blocks.SOUL_FIRE,
			Blocks.CAMPFIRE,
			Blocks.SOUL_CAMPFIRE,
			Blocks.SWEET_BERRY_BUSH,
			Blocks.CACTUS
	};
	
	private boolean[] bits;
	
	private boolean passable;
	
	private boolean breakable;
	
	private BlockType(int bits) {
		this(bits, false);
	}
	
	private BlockType(int bits, boolean passable) {
		this(bits, passable, !passable);
	}
	
	private BlockType(int bits, boolean passable, boolean breakable) {
		this.bits = new boolean[] {
				(bits & 2) != 0,
				(bits & 1) != 0
		};
		
		this.passable = passable;
		this.breakable = breakable;
	}
	
	public boolean[] getBits() {
		return bits;
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
	
	public static BlockType fromBits(boolean b1, boolean b2) {
		return b1 ? b2 ? DANGER : WATER : b2 ? SOLID : AIR;
	}
	
}
