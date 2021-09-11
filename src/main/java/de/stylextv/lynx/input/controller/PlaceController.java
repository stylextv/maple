package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.util.world.Offset;
import net.minecraft.util.math.BlockPos;

public class PlaceController {
	
	public static boolean canPlaceAt(BlockPos pos) {
		for(Offset o : Offset.DIRECT_BLOCK_NEIGHBOURS) {
			
			int x = pos.getX() + o.getBlockX();
			int y = pos.getY() + o.getBlockY();
			int z = pos.getZ() + o.getBlockZ();
			
			if(canPlaceAgainst(x, y, z)) return true;
		}
		
		return false;
	}
	
	public static boolean canPlaceAgainst(int x, int y, int z) {
		return BreakController.isBreakable(x, y, z);
	}
	
}
