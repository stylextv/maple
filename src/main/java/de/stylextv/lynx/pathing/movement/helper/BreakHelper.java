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

public class BreakHelper {
	
	private List<BlockTarget> targets = new CopyOnWriteArrayList<>();
	
	public void collectBlocks(Node n, int height) {
		collectBlocks(n, 0, height);
	}
	
	public void collectBlocks(Node n, int offset, int height) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		collectBlocks(x, y, z, offset, height);
	}
	
	public void collectBlocks(int x, int y, int z, int offset, int height) {
		for(int i = 0; i < height; i++) {
			
			collectBlock(x, y + offset + i, z);
		}
	}
	
	public void collectBlock(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(type.isPassable() || !type.isBreakable()) return;
		
		targets.add(new BlockTarget(x, y, z));
	}
	
	public double cost() {
		int sum = 0;
		
		for(BlockTarget target : targets) {
			
			BlockPos pos = target.getPos();
			
			sum += Cost.breakCost(pos);
		}
		
		return sum;
	}
	
	public void onRenderTick() {
		InputController.setPressed(InputAction.MOVE_FORWARD, false);
		
		for(BlockTarget target : targets) {
			
			if(!target.isBreakable()) {
				
				targets.remove(target);
				
				continue;
			}
			
			if(target.continueTransforming()) return;
		}
	}
	
	public boolean hasTargets() {
		return !targets.isEmpty();
	}
	
}
