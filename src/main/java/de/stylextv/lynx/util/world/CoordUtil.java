package de.stylextv.lynx.util.world;

import net.minecraft.util.math.BlockPos;

public class CoordUtil {
	
	public static int chunkToRegionPos(int i) {
		return i >> 5;
	}
	
	public static int blockToChunkPos(int i) {
		return i >> 4;
	}
	
	public static int unitToBlockPos(double d) {
		return (int) Math.floor(d);
	}
	
	public static long posAsLong(int x, int y, int z) {
		return BlockPos.asLong(x, y, z);
	}
	
}
