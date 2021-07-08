package de.stylextv.lynx.cache;

import java.util.HashMap;

import de.stylextv.lynx.context.WorldContext;
import net.minecraft.util.math.BlockPos;

public class CacheManager {
	
	private static HashMap<String, CachedWorld> worlds = new HashMap<>();
	
	private static CachedWorld currentWorld;
	
	public static void enterWorld() {
		String name = WorldContext.getLevelName();
		
		currentWorld = getCache(name);
		
		currentWorld.enter();
	}
	
	public static void exitWorld() {
		currentWorld.exit();
		
		currentWorld = null;
	}
	
	public static BlockType getBlockType(int x, int y, int z) {
		return getBlockType(new BlockPos(x, y, z));
	}
	
	public static BlockType getBlockType(BlockPos pos) {
		return currentWorld.getBlockType(pos);
	}
	
	private static CachedWorld getCache(String name) {
		CachedWorld cache = worlds.get(name);
		
		if(cache == null) {
			cache = new CachedWorld(name);
			
			worlds.put(name, cache);
		}
		
		return cache;
	}
	
}
