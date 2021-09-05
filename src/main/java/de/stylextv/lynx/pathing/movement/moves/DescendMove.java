package de.stylextv.lynx.pathing.movement.moves;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.movement.Move;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.movements.DescendMovement;
import de.stylextv.lynx.pathing.movement.movements.FallMovement;

public class DescendMove extends Move {
	
	public DescendMove(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public Movement apply(Node n, PathFinder finder) {
		int dx = getDeltaX();
		int dy = getDeltaY();
		int dz = getDeltaZ();
		
		Node destination = finder.getAdjacentNode(n, dx, dy, dz);
		
		while(destination.getY() > 0) {
			
			if(destination.getType() == BlockType.WATER) break;
			
			Node below = finder.getAdjacentNode(n, dx, dy - 1, dz);
			
			BlockType type = below.getType();
			
			if(!type.isPassable()) break;
			
			dy--;
			
			destination = finder.getAdjacentNode(n, dx, dy, dz);
		}
		
		int fallDistance = n.getY() - destination.getY();
		
		if(fallDistance == 1) return new DescendMovement(n, destination);
		
		return new FallMovement(n, destination);
	}
	
}
