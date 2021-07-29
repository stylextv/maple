package de.stylextv.lynx.cache;

import java.util.HashMap;

import de.stylextv.lynx.context.LevelContext;
import net.minecraft.core.BlockPos;

public class CacheManager {
	
	private static HashMap<String, CachedLevel> levels = new HashMap<>();
	
	private static CachedLevel level;
	
	public static void enterLevel() {
		exitLevel();
		
		String name = LevelContext.getLevelName();
		
		level = getLevel(name);
		
		level.enter();
	}
	
	public static void exitLevel() {
		if(level == null) return;
		
		level.exit();
		
		level = null;
	}
	
	public static BlockType getBlockType(int x, int y, int z) {
		return getBlockType(new BlockPos(x, y, z));
	}
	
	public static BlockType getBlockType(BlockPos pos) {
		if(!LevelContext.isInsideBorder(pos)) return BlockType.UNBREAKABLE;
		
		return level.getBlockType(pos);
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
