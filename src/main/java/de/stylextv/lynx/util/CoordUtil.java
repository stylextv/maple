package de.stylextv.lynx.util;

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
	
}
