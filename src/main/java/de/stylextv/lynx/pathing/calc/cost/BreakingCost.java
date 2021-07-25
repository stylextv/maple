package de.stylextv.lynx.pathing.calc.cost;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.context.LevelContext;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BreakingCost {
	
	private static final int BASE_COST = 10;
	
	private static final int HARDNESS_MULTIPLIER = 30;
	
	// TODO factor in pickaxe/hand
	
	public static int getCost(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(type.isPassable()) return 0;
		
		if(!type.isBreakable()) return Cost.INFINITY;
		
		ClientLevel level = LevelContext.level();
		
		BlockPos pos = new BlockPos(x, y, z);
		
		BlockState state = level.getBlockState(pos);
		
		float hardness = state.getDestroySpeed(level, pos);
		
		if(hardness < 0) return Cost.INFINITY;
		
		return BASE_COST; // + (int) (hardness * HARDNESS_MULTIPLIER)
	}
	
}
