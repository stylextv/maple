package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.cache.block.BlockType;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;

public class BumpHelper extends MovementHelper<Movement> {
	
	public BumpHelper(Movement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		Movement m = getMovement();
		
		if(!m.isDiagonal()) return 0;
		
		Node source = getSource();
		Node destination = getDestination();
		
		if(isBlocked(destination, 2)) return Cost.INFINITY;
		
		int y = Math.max(source.getY(), destination.getY());
		
		boolean b1 = isBlocked(source.getX(), y, destination.getZ(), 2);
		boolean b2 = isBlocked(destination.getX(), y, source.getZ(), 2);
		
		if(b1 && b2) return Cost.INFINITY;
		
		return b1 || b2 ? Cost.BUMP_INTO_CORNER : 0;
	}
	
	private static boolean isBlocked(Node n, int height) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return isBlocked(x, y, z, height);
	}
	
	private static boolean isBlocked(int x, int y, int z, int height) {
		return isBlocked(x, y, z, 0, height);
	}
	
	private static boolean isBlocked(int x, int y, int z, int offset, int height) {
		for(int i = 0; i < height; i++) {
			
			if(isBlocked(x, y + offset + i, z)) return true;
		}
		
		return false;
	}
	
	private static boolean isBlocked(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		return !type.isPassable();
	}
	
}
