package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.input.controller.BreakController;
import de.stylextv.lynx.input.target.BlockTarget;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.world.BlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;

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
			
			while(true) {
				
				BlockType type = CacheManager.getBlockType(pos);
				
				boolean unbreakable = type.isSolid() && !type.isBreakable();
				
				if(unbreakable || !BreakController.isSafeToBreak(pos)) return Cost.INFINITY;
				
				sum += Cost.breakCost(pos);
				
				pos = pos.up();
				
				if(hasTarget(pos)) break;
				
				BlockState state = BlockInterface.getState(pos);
				
				Block block = state.getBlock();
				
				boolean falls = block instanceof FallingBlock;
				
				if(!falls) break;
			}
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
