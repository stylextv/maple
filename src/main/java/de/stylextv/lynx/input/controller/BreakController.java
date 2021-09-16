package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.util.world.Offset;
import de.stylextv.lynx.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BreakController {
	
	public static boolean isSafeToBreak(BlockPos pos) {
		for(Offset o : Offset.UPPER_DIRECT_BLOCK_NEIGHBOURS) {
			
			int x = pos.getX() + o.getBlockX();
			int y = pos.getY() + o.getBlockY();
			int z = pos.getZ() + o.getBlockZ();
			
			BlockState state = BlockInterface.getState(x, y, z);
			
			if(!state.getFluidState().isEmpty()) return false;
		}
		
		return true;
	}
	
	public static boolean isBreakable(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		return isBreakable(x, y, z);
	}
	
	public static boolean isBreakable(int x, int y, int z) {
		BlockState state = BlockInterface.getState(x, y, z);
		
		return state.getMaterial().blocksMovement();
	}
	
}
