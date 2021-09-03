package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.world.BlockInterface;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SpeedHelper extends MovementHelper {
	
	public SpeedHelper(Movement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		Movement m = getMovement();
		
		if(m.isVerticalOnly()) return 0;
		
		Node n = m.getDestination();
		
		boolean inWater = n.getType() == BlockType.WATER;
		
		if(!inWater && needsSupport) {
			
		}
		
		boolean isDiagonal = m.isDiagonal();
		
		if(inWater) return isDiagonal ? Cost.SWIM_DIAGONALLY : Cost.SWIM_STRAIGHT;
		
		int x = n.getX();
		int y = n.getY() - 1;
		int z = n.getZ();
		
		BlockState state = BlockInterface.getState(x, y, z);
		
		Block block = state.getBlock();
		
		float f = block.getSpeedFactor();
		
		double cost = isDiagonal ? Cost.WALK_DIAGONALLY : Cost.WALK_STRAIGHT;
		
		return cost / f;
	}
	
}
