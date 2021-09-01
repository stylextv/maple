package de.stylextv.lynx.world;

import de.stylextv.lynx.context.LevelContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.border.WorldBorder;

public class WorldBorderInterface {
	
	public static boolean isInside(BlockPos pos) {
		return isInside(pos.getX(), pos.getZ());
	}
	
	// TODO getMinX, getMaxX, ... are too slow!
	public static boolean isInside(int x, int z) {
		WorldBorder border = getBorder();
		
		return x + 1 > border.getMinX() && x < border.getMaxX() && z + 1 > border.getMinZ() && z < border.getMaxZ();
	}
	
	public static WorldBorder getBorder() {
		return LevelContext.level().getWorldBorder();
	}
	
}
