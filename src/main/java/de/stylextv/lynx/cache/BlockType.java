package de.stylextv.lynx.cache;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public enum BlockType {
	
	AIR(0, Blocks.AIR, true),
	SOLID(1, Blocks.STONE),
	WATER(2, Blocks.WATER, true),
	DANGER(3, Blocks.LAVA),
	UNLOADED(-1, Blocks.AIR, true),
	VOID(-1, Blocks.AIR, true),
	UNBREAKABLE(-1, Blocks.BEDROCK, false, false);
	
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
	
	private BlockState state;
	
	private boolean passable;
	
	private boolean breakable;
	
	private BlockType(int bits, Block block) {
		this(bits, block, false);
	}
	
	private BlockType(int bits, Block block, boolean passable) {
		this(bits, block, passable, !passable);
	}
	
	private BlockType(int bits, Block block, boolean passable, boolean breakable) {
		this.bits = new boolean[] {
				(bits & 2) != 0,
				(bits & 1) != 0
		};
		
		this.state = block.defaultBlockState();
		
		this.passable = passable;
		this.breakable = breakable;
	}
	
	public boolean[] getBits() {
		return bits;
	}
	
	public BlockState getState() {
		return state;
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
		
		Fluid fluid = state.getFluidState().getType();
		
		if(fluid.equals(Fluids.FLOWING_WATER)) return DANGER;
		
		boolean isWater = fluid.equals(Fluids.WATER);
		
		boolean aboveMagma = blockBelow.equals(Blocks.MAGMA_BLOCK);
		
		if(isWater) return aboveMagma ? DANGER : WATER;
		
		boolean isSolid = state.getMaterial().blocksMotion();
		
		if(isSolid) return SOLID;
		
		return aboveMagma ? DANGER : AIR;
	}
	
	public static BlockType fromBits(boolean b1, boolean b2) {
		return b1 ? b2 ? DANGER : WATER : b2 ? SOLID : AIR;
	}
	
}
