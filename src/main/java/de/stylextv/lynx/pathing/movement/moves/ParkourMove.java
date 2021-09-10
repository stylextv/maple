package de.stylextv.lynx.pathing.movement.moves;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.movement.Move;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.ParkourHelper;
import de.stylextv.lynx.pathing.movement.movements.ParkourMovement;

public class ParkourMove extends Move {
	
	private static final int MAX_DISTANCE = 5;
	
	public ParkourMove(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public Movement apply(Node n, PathFinder finder) {
		if(ParkourHelper.isObstructed(n, 2, 1)) return null;
		
		int dx = getDeltaX();
		int dy = getDeltaX();
		int dz = getDeltaX();
		
		for(int i = 1; i <= MAX_DISTANCE; i++) {
			
			int ox = dx * i;
			int oz = dz * i;
			
			Node destination = finder.getAdjacentNode(n, ox, dy, oz);
			
			if(ParkourHelper.isObstructed(destination, 3)) return null;
			
			int x = destination.getX();
			int y = destination.getY() - 1;
			int z = destination.getZ();
			
			BlockType type = CacheManager.getBlockType(x, y, z);
			
			if(!type.isPassable()) {
				
				if(i == 1) return null;
				
				return new ParkourMovement(n, destination, dx, dz, i);
			}
		}
		
		return null;
	}
	
}
