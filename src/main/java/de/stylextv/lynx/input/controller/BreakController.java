package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.util.world.Offset;
import de.stylextv.lynx.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BreakController {
	
	public static boolean isSafeToBreak(BlockPos pos) {
		for(Offset o : Offset.UPPER_DIRECT_BLOCK_NEIGHBOURS) {
			
			int dx = o.getBlockX();
			int dy = o.getBlockY();
			int dz = o.getBlockZ();
			
			int x = pos.getX() + dx;
			int y = pos.getY() + dy;
			int z = pos.getZ() + dz;
			
			BlockState state = BlockInterface.getState(x, y, z);
			
			int level = state.getFluidState().getLevel();
			
			if(level == 0) continue;
			
			if(dy == 1 || level > 1) return false;
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
