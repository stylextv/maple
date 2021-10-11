package de.stylextv.maple.pathing.movement.helper;

import java.util.HashMap;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class StepHelper extends MovementHelper<Movement> {
	
	private static final HashMap<Block, Float> SPEED_MULTIPLIERS = new HashMap<>();
	
	static {
		SPEED_MULTIPLIERS.put(Blocks.COBWEB, 0.25f);
		SPEED_MULTIPLIERS.put(Blocks.SWEET_BERRY_BUSH, 0.8f);
	}
	
	public StepHelper(Movement m) {
		super(m);
	}
	
	// TODO slowness effect from diagonally adjacent blocks
	@Override
	public double cost() {
		Movement m = getMovement();
		
		Node destination = m.getDestination();
		
		boolean inWater = destination.getType() == BlockType.WATER;
		
		boolean isDiagonal = m.isDiagonal();
		
		boolean needsSupport = isDiagonal || m.isDownwards();
		
		int x = destination.getX();
		int y = destination.getY();
		int z = destination.getZ();
		
		if(needsSupport && !inWater) {
			
			BlockType type = CacheManager.getBlockType(x, y - 1, z);
			
			if(!type.isSolid()) return Cost.INFINITY;
		}
		
		if(m.isVerticalOnly()) return 0;
		
		if(inWater) return isDiagonal ? Cost.SWIM_DIAGONALLY : Cost.SWIM_STRAIGHT;
		
		BlockState state = BlockInterface.getState(x, y - 1, z);
		
		Block block = state.getBlock();
		
		float f = block.getVelocityMultiplier();
		
		float m1 = getSpeedMultiplier(x, y, z);
		float m2 = getSpeedMultiplier(x, y + 1, z);
		
		f *= Math.min(m1, m2);
		
		double cost = isDiagonal ? Cost.WALK_DIAGONALLY : Cost.WALK_STRAIGHT;
		
		return cost / f;
	}
	
	public static float getSpeedMultiplier(int x, int y, int z) {
		BlockState state = BlockInterface.getState(x, y, z);
		
		Block block = state.getBlock();
		
		return SPEED_MULTIPLIERS.getOrDefault(block, 1f);
	}
	
}
