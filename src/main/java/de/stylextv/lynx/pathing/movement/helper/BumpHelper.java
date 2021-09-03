package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class BumpHelper extends MovementHelper {
	
	public BumpHelper(Movement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		Movement m = getMovement();
		
		if(!m.isDiagonal()) return 0;
		
		Node source = m.getSource();
		Node destination = m.getDestination();
		
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
		for(int i = 0; i < height; i++) {
			
			if(isBlocked(x, y + i, z)) return true;
		}
		
		return false;
	}
	
	private static boolean isBlocked(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		return !type.isPassable();
	}
	
}
