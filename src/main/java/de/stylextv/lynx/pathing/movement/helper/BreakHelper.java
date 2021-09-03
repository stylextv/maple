package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.input.target.BlockTarget;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.movement.Movement;
import net.minecraft.core.BlockPos;

public class BreakHelper extends TargetHelper {
	
	public BreakHelper(Movement m) {
		super(m);
	}
	
	@Override
	public void collectBlock(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(type.isPassable()) return;
		
		if(!hasTarget(x, y, z)) addTarget(x, y, z);
	}
	
	@Override
	public double cost() {
		int sum = 0;
		
		for(BlockTarget target : getTargets()) {
			
			BlockPos pos = target.getPos();
			
			sum += Cost.breakCost(pos);
		}
		
		return sum;
	}
	
	public boolean onRenderTick() {
		if(!hasTargets()) return false;
		
		for(BlockTarget target : getTargets()) {
			
			if(!target.isBreakable()) {
				
				removeTarget(target);
				
				continue;
			}
			
			if(target.continueTransforming()) return true;
		}
		
		return false;
	}
	
}
