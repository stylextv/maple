package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.target.BlockTarget;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.movement.Movement;

public class PlaceHelper extends TargetHelper {
	
	public PlaceHelper(Movement m) {
		super(m);
	}
	
	@Override
	public void collectBlock(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(!type.isPassable()) return;
		
		if(!hasTarget(x, y, z)) addTarget(x, y, z);
	}
	
	// TODO check if canPlaceAt
	@Override
	public double cost() {
		int l = getTargets().size();
		
		return l * Cost.placeCost();
	}
	
	public boolean onRenderTick() {
		if(!hasTargets()) return false;
		
		for(BlockTarget target : getTargets()) {
			
			if(!target.isPlaceable()) {
				
				removeTarget(target);
				
				continue;
			}
			
			if(target.continueTransforming()) {
				
				InputController.setPressed(InputAction.SNEAK, true);
				
				return true;
			}
		}
		
		return false;
	}
	
}
