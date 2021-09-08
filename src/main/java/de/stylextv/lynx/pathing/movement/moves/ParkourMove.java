package de.stylextv.lynx.pathing.movement.moves;

import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.movement.Move;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.BumpHelper;
import de.stylextv.lynx.pathing.movement.movements.ParkourMovement;

public class ParkourMove extends Move {
	
	private static final int MAX_DISTANCE = 5;
	
	public ParkourMove(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public Movement apply(Node n, PathFinder finder) {
		if(BumpHelper.isBlocked(n, 2, 1)) return null;
		
		for(int i = 1; i <= MAX_DISTANCE; i++) {
			
			int dx = getDeltaX() * i;
			int dy = getDeltaY();
			int dz = getDeltaZ() * i;
			
			Node destination = finder.getAdjacentNode(n, dx, dy, dz);
			
			if(BumpHelper.isBlocked(destination, 3)) return null;
			
			if(BumpHelper.isBlocked(destination, -1, 1)) {
				
				if(i == 1) return null;
				
				return new ParkourMovement(n, destination);
			}
		}
		
		return null;
	}
	
}
