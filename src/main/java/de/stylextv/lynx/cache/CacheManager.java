package de.stylextv.lynx.cache;

import java.util.HashMap;

import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.util.world.CoordUtil;
import de.stylextv.lynx.world.WorldBorderInterface;
import net.minecraft.util.math.BlockPos;

public class CacheManager {
	
	private static HashMap<String, CachedWorld> worlds = new HashMap<>();
	
	private static CachedWorld world;
	
	public static void enterWorld() {
		String name = WorldContext.getLevelName();
		
		CachedWorld w = getWorld(name);
		
		if(w.equals(world)) return;
		
		exitWorld();
		
		world = w;
		
		world.enter();
	}
	
	public static void exitWorld() {
		if(world == null) return;
		
		world.exit();
		
		world = null;
	}
	
	public static BlockType getBlockType(double x, double y, double z) {
		int blockX = CoordUtil.unitToBlockPos(x);
		int blockY = CoordUtil.unitToBlockPos(y);
		int blockZ = CoordUtil.unitToBlockPos(z);
		
		return getBlockType(blockX, blockY, blockZ);
	}
	
	public static BlockType getBlockType(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		return getBlockType(x, y, z);
	}
	
	public static BlockType getBlockType(int x, int y, int z) {
		if(!WorldBorderInterface.isInside(x, z)) return BlockType.UNBREAKABLE;
		
		return world.getBlockType(x, y, z);
	}
	
	private static CachedWorld getWorld(String name) {
		CachedWorld cache = worlds.get(name);
		
		if(cache == null) {
			cache = new CachedWorld(name);
			
			worlds.put(name, cache);
		}
		
		return cache;
	}
	
	public static CachedWorld getWorld() {
		return world;
	}
	
}
