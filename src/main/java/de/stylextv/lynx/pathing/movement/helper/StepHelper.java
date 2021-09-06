package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.world.BlockInterface;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class StepHelper extends MovementHelper {
	
	public StepHelper(Movement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		Movement m = getMovement();
		
		if(m.isVerticalOnly()) return 0;
		
		Node destination = m.getDestination();
		Node source = m.getSource();
		
		boolean inWater = destination.getType() == BlockType.WATER;
		
		int x = destination.getX();
		int y = destination.getY();
		int z = destination.getZ();
		
		int disX = Math.abs(source.getX() - x);
		int disY = Math.abs(source.getY() - y);
		int disZ = Math.abs(source.getZ() - z);
		
		int dis = disX + disY + disZ;
		
		boolean needsSupport = dis != 1 || m.isDownwards();
		
		if(needsSupport && !inWater) {
			
			BlockType type = CacheManager.getBlockType(x, y - 1, z);
			
			if(type != BlockType.SOLID) return Cost.INFINITY;
		}
		
		boolean isDiagonal = m.isDiagonal();
		
		if(inWater) return isDiagonal ? Cost.SWIM_DIAGONALLY : Cost.SWIM_STRAIGHT;
		
		BlockState state = BlockInterface.getState(x, y - 1, z);
		
		Block block = state.getBlock();
		
		float f = block.getSpeedFactor();
		
		double cost = isDiagonal ? Cost.WALK_DIAGONALLY : Cost.WALK_STRAIGHT;
		
		return cost / f;
	}
	
}
