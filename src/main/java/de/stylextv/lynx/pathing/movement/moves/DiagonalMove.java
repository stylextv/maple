package de.stylextv.lynx.pathing.movement.moves;

import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.movement.Move;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.movements.DiagonalMovement;

public class DiagonalMove extends Move {
	
	public DiagonalMove(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public Movement apply(Node n, PathFinder finder) {
		Node destination = finder.getAdjacentNode(n, getDeltaX(), getDeltaY(), getDeltaZ());
		
		return new DiagonalMovement(n, destination);
	}
	
}
