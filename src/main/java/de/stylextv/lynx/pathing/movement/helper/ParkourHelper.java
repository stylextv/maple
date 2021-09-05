package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.movement.Movement;

public class ParkourHelper extends MovementHelper {
	
	public ParkourHelper(Movement m) {
		super(m);
	}
	
	// TODO check if blocks are in the way or if jump is too far
	@Override
	public double cost() {
		Movement m = getMovement();
		
		int dis = m.horizontalDistance();
		
		if(dis > 5) return Cost.INFINITY;
		
		return Cost.JUMP + dis * Cost.SPRINT_STRAIGHT;
	}
	
}
