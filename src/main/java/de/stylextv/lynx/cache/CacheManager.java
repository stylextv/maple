package de.stylextv.lynx.cache;

import java.util.HashMap;

import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.world.WorldBorderInterface;
import net.minecraft.core.BlockPos;

public class CacheManager {
	
	private static HashMap<String, CachedLevel> levels = new HashMap<>();
	
	private static CachedLevel level;
	
	public static void enterLevel() {
		String name = LevelContext.getLevelName();
		
		CachedLevel l = getLevel(name);
		
		if(l.equals(level)) return;
		
		exitLevel();
		
		level = l;
		
		level.enter();
	}
	
	public static void exitLevel() {
		if(level == null) return;
		
		level.exit();
		
		level = null;
	}
	
	public static BlockType getBlockType(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		return getBlockType(x, y, z);
	}
	
	public static BlockType getBlockType(int x, int y, int z) {
		if(!WorldBorderInterface.isInside(x, z)) return BlockType.UNBREAKABLE;
		
		return level.getBlockType(x, y, z);
	}
	
	private static CachedLevel getLevel(String name) {
		CachedLevel cache = levels.get(name);
		
		if(cache == null) {
			cache = new CachedLevel(name);
			
			levels.put(name, cache);
		}
		
		return cache;
	}
	
}
