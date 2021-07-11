package de.stylextv.lynx.pathing.calc.cost;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.context.WorldContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public class BreakingCost {
	
	private static final int BASE_COST = 10;
	
	private static final int HARDNESS_MULTIPLIER = 30;
	
	// TODO factor in pickaxe/hand
	
	public static int getCost(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(type.isPassable()) return 0;
		
		ClientWorld w = WorldContext.world();
		
		BlockPos pos = new BlockPos(x, y, z);
		
		BlockState state = w.getBlockState(pos);
		
		float hardness = state.getDestroySpeed(w, pos);
		
		return BASE_COST + (int) (hardness * HARDNESS_MULTIPLIER);
	}
	
}
