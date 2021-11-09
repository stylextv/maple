package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.cache.block.BlockType;
import de.stylextv.maple.input.controller.AwarenessController;
import de.stylextv.maple.input.controller.BreakController;
import de.stylextv.maple.input.target.targets.BreakableTarget;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.movement.Movement;
import net.minecraft.util.math.BlockPos;

public class BreakHelper extends TargetHelper<BreakableTarget> {
	
	public BreakHelper(Movement m) {
		super(m);
	}
	
	@Override
	public void collectBlock(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(type.isPassable()) return;
		
		if(!hasTarget(x, y, z)) {
			
			BreakableTarget target = new BreakableTarget(x, y, z);
			
			addTarget(target);
		}
	}
	
	@Override
	public double cost() {
		int sum = 0;
		
		for(BreakableTarget target : getTargets()) {
			
			BlockPos pos = target.getPos();
			
			while(true) {
				
				sum += costOfBlock(pos);
				
				pos = pos.up();
				
				if(hasTarget(pos)) break;
				
				boolean falls = AwarenessController.isFallingBlock(pos);
				
				if(!falls) break;
			}
		}
		
		return sum;
	}
	
	private double costOfBlock(BlockPos pos) {
		BlockType type = CacheManager.getBlockType(pos);
		
		boolean unbreakable = type.isSolid() && !type.isBreakable();
		
		if(unbreakable || !BreakController.isSafeToBreak(pos)) return Cost.INFINITY;
		
		return Cost.breakCost(pos);
	}
	
	public boolean onRenderTick() {
		if(!hasTargets()) return false;
		
		for(BreakableTarget target : getTargets()) {
			
			if(target.isBroken()) {
				
				removeTarget(target);
				
				continue;
			}
			
			if(target.continueBreaking(false)) return true;
		}
		
		return false;
	}
	
}
