package de.stylextv.maple.input.target;

import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockTarget {
	
	private BlockPos pos;
	
	public BlockTarget(int x, int y, int z) {
		this(new BlockPos(x, y, z));
	}
	
	public BlockTarget(BlockPos pos) {
		this.pos = pos;
	}
	
	public BlockState state() {
		return BlockInterface.getState(pos);
	}
	
	@Override
	public int hashCode() {
		return pos.hashCode();
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
}
