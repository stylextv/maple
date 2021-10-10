package de.stylextv.maple.pathing.movement.moves;

import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.calc.PathFinder;
import de.stylextv.maple.pathing.movement.Move;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.movements.PillarMovement;

public class PillarMove extends Move {
	
	public PillarMove(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public Movement apply(Node n, PathFinder finder) {
		Node destination = finder.getAdjacentNode(n, getDeltaX(), getDeltaY(), getDeltaZ());
		
		return new PillarMovement(n, destination);
	}
	
}
