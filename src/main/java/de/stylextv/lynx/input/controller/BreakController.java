package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.world.BlockInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BreakController {
	
	public static boolean isBreakable(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		return isBreakable(x, y, z);
	}
	
	public static boolean isBreakable(int x, int y, int z) {
		BlockState state = BlockInterface.getState(x, y, z);
		
		return state.getMaterial().blocksMotion();
	}
	
}
