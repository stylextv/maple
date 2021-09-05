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
		return Cost.INFINITY;
	}
	
}
