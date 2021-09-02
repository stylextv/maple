package de.stylextv.lynx.pathing.movement.helper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.target.BlockTarget;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

public class PlaceHelper {
	
	private List<BlockTarget> targets = new CopyOnWriteArrayList<>();
	
	public void collectBlock(Node n) {
		collectBlock(n, 0);
	}
	
	public void collectBlock(Node n, int offset) {
		int x = n.getX();
		int y = n.getY() + offset;
		int z = n.getZ();
		
		collectBlock(x, y, z);
	}
	
	public void collectBlock(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(!type.isPassable()) return;
		
		if(!hasTarget(x, y, z)) targets.add(new BlockTarget(x, y, z));
	}
	
	public double cost() {
		int l = targets.size();
		
		return l * Cost.placeCost();
	}
	
	public boolean onRenderTick() {
		if(!hasTargets()) return false;
		
		for(BlockTarget target : targets) {
			
			if(!target.isPlaceable()) {
				
				targets.remove(target);
				
				continue;
			}
			
			if(target.continueTransforming()) {
				
				InputController.setPressed(InputAction.SNEAK, true);
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasTarget(int x, int y, int z) {
		for(BlockTarget target : targets) {
			
			BlockPos pos = target.getPos();
			
			if(pos.getX() == x && pos.getY() == y && pos.getZ() == z) return true;
		}
		
		return false;
	}
	
	public boolean hasTargets() {
		return !targets.isEmpty();
	}
	
}
