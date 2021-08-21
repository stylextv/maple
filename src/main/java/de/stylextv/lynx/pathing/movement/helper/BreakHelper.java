package de.stylextv.lynx.pathing.movement.helper;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

public class BreakHelper {
	
	private List<BlockPos> blocks = new ArrayList<>();
	
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
		
		blocks.add(new BlockPos(x, y, z));
	}
	
	public double cost() {
		int sum = 0;
		
		for(BlockPos pos : blocks) {
			
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			
			sum += Cost.breakCost(x, y, z);
		}
		
		return sum;
	}
	
	public void onRenderTick() {
		InputController.setPressed(InputAction.MOVE_FORWARD, false);
		
		
	}
	
	public boolean hasBlocks() {
		return !blocks.isEmpty();
	}
	
}
