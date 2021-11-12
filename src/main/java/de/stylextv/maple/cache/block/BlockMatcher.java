package de.stylextv.maple.cache.block;

import de.stylextv.maple.cache.block.matchers.DangerBlockMatcher;
import de.stylextv.maple.cache.block.matchers.FluidBlockMatcher;
import de.stylextv.maple.cache.block.matchers.SolidBlockMatcher;
import de.stylextv.maple.cache.block.matchers.UnbreakableBlockMatcher;
import de.stylextv.maple.world.interact.Openable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockMatcher {
	
	public static final BlockMatcher DANGER = new DangerBlockMatcher();
	
	public static final BlockMatcher UNBREAKABLE = new UnbreakableBlockMatcher();
	
	public static final BlockMatcher SOLID = new SolidBlockMatcher();
	
	public static final BlockMatcher FLUID = new FluidBlockMatcher();
	
	public static final BlockMatcher OPENABLE = new BlockMatcher() {
		
		@Override
		public BlockType match(Block block) {
			Openable o = Openable.fromBlock(block);
			
			if(o != null) return BlockType.AIR;
			
			return null;
		}
	};
	
	private static final BlockMatcher[] MATCHERS = new BlockMatcher[] {
			DANGER,
			UNBREAKABLE,
			OPENABLE,
			SOLID,
			FLUID
	};
	
	public BlockType match(BlockState state, BlockState above, BlockState below, BlockPos pos) {
		return match(state, above, below);
	}
	
	public BlockType match(BlockState state, BlockState above, BlockState below) {
		return match(state);
	}
	
	public BlockType match(BlockState state) {
		Block block = state.getBlock();
		
		return match(block);
	}
	
	public BlockType match(Block block) {
		return null;
	}
	
	public static BlockType matchStates(BlockState state, BlockState above, BlockState below, BlockPos pos) {
		for(BlockMatcher m : MATCHERS) {
			
			BlockType type = m.match(state, above, below, pos);
			
			if(type != null) return type;
		}
		
		return BlockType.AIR;
	}
	
}
