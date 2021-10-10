package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class StepHelper extends MovementHelper<Movement> {
	
	public StepHelper(Movement m) {
		super(m);
	}
	
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
		
		double cost = isDiagonal ? Cost.WALK_DIAGONALLY : Cost.WALK_STRAIGHT;
		
		return cost / f;
	}
	
}
