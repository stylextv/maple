package de.stylextv.lynx.world;

import de.stylextv.lynx.context.WorldContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.border.WorldBorder;

public class WorldBorderInterface {
	
	public static boolean isInside(BlockPos pos) {
		return isInside(pos.getX(), pos.getZ());
	}
	
	// TODO don't use native methods (too slow)!
	public static boolean isInside(int x, int z) {
		WorldBorder border = getBorder();
		
		return border.contains(x, z);
	}
	
	public static WorldBorder getBorder() {
		return WorldContext.world().getWorldBorder();
	}
	
}
