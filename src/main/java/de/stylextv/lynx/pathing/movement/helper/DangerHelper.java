package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.world.avoidance.Avoidance;

public class DangerHelper extends MovementHelper {
	
	public DangerHelper(Movement m) {
		super(m);
	}
	
	// TODO cost for walking close to monsters
	@Override
	public double cost() {
		Movement m = getMovement();
		
		Node source = m.getSource();
		Node destination = m.getDestination();
		
		int dy = source.getY() - destination.getY();
		
		if(dy > 3 && destination.getType() != BlockType.WATER) return Cost.INFINITY;
		
		if(isNearDanger(destination)) return Cost.INFINITY;
		
		int x = destination.getX();
		int y = destination.getY();
		int z = destination.getZ();
		
		BlockType type = CacheManager.getBlockType(x, y + 1, z);
		
		if(type == BlockType.WATER) return Cost.INFINITY;
		
		if(m.isDiagonal()) {
			
			int x2 = source.getX();
			int z2 = source.getZ();
			
			int y2 = Math.max(source.getY(), destination.getY());
			
			boolean b = isNearDanger(x, y2, z2) || isNearDanger(x2, y2, z);
			
			if(b) return Cost.INFINITY;
		}
		
		return Avoidance.getCost(x, y, z);
	}
	
	private static boolean isNearDanger(Node n) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return isNearDanger(x, y, z);
	}
	
	private static boolean isNearDanger(int x, int y, int z) {
		return isDangerous(x, y, z, -1, 3);
	}
	
	private static boolean isDangerous(int x, int y, int z, int offset, int height) {
		for(int i = 0; i < height; i++) {
			
			if(isDangerous(x, y + offset + i, z)) return true;
		}
		
		return false;
	}
	
	private static boolean isDangerous(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		return type == BlockType.DANGER;
	}
	
}
