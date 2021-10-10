package de.stylextv.maple.pathing.movement.moves;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.calc.PathFinder;
import de.stylextv.maple.pathing.movement.Move;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.movements.DescendMovement;
import de.stylextv.maple.pathing.movement.movements.FallMovement;

public class DescendMove extends Move {
	
	public DescendMove(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public Movement apply(Node n, PathFinder finder) {
		int dx = getDeltaX();
		int dy = getDeltaY();
		int dz = getDeltaZ();
		
		Node destination;
		
		while(true) {
			
			destination = finder.getAdjacentNode(n, dx, dy, dz);
			
			if(destination.getY() <= 0 || destination.getType() == BlockType.WATER) break;
			
			Node below = finder.getAdjacentNode(n, dx, dy - 1, dz);
			
			BlockType type = below.getType();
			
			if(!type.isPassable()) break;
			
			dy--;
		}
		
		int fallDistance = n.getY() - destination.getY();
		
		if(fallDistance == 1) return new DescendMovement(n, destination);
		
		return new FallMovement(n, destination, fallDistance);
	}
	
}
