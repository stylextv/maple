package de.stylextv.lynx.pathing.movement.moves;

import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.movement.Move;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.movements.ParkourMovement;

public class ParkourMove extends Move {
	
	private static final int MAX_DISTANCE = 5;
	
	public ParkourMove(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public Movement apply(Node n, PathFinder finder) {
		int l = 2;
		
		Movement movement = null;
		
		while(l <= MAX_DISTANCE) {
			
			int dx = getDeltaX() * l;
			int dy = getDeltaY();
			int dz = getDeltaZ() * l;
			
			Node destination = finder.getAdjacentNode(n, dx, dy, dz);
			
			Movement m = new ParkourMovement(n, destination);
			
			if(movement == null) movement = m;
			
			double cost = m.favoredCost(finder.getFavoring());
			
			if(cost < Cost.INFINITY) return m;
			
			l++;
		}
		
		return movement;
	}
	
}
