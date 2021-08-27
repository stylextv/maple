package de.stylextv.lynx.input.controller;

import net.minecraft.core.BlockPos;

public class PlaceController {
	
	public static boolean canPlaceAgainst(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		
		return canPlaceAgainst(pos);
	}
	
	public static boolean canPlaceAgainst(BlockPos pos) {
		return BreakController.isBreakable(pos);
	}
	
}
