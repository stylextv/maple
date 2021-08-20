package de.stylextv.lynx.pathing.movement.helper;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

public class PlaceHelper {
	
	private List<BlockPos> blocks = new ArrayList<>();
	
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
		
		blocks.add(new BlockPos(x, y, z));
	}
	
	public double cost() {
		int l = blocks.size();
		
		return l * Cost.placeCost();
	}
	
}
