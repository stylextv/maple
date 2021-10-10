package de.stylextv.maple.util.world;

import de.stylextv.maple.util.MathUtil;
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
	
	public static int posInChunk(int i) {
		return i & 15;
	}
	
	public static long posAsLong(int x, int y, int z) {
		return BlockPos.asLong(x, y, z);
	}
	
	public static String formatDistance(double d) {
		long l = Math.round(d);
		
		if(l < 1000) return l + " blocks";
		
		return MathUtil.formatNumber(l / 1000.0) + " km";
	}
	
}
