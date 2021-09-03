package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.pathing.movement.Movement;

public abstract class MovementHelper {
	
	private Movement movement;
	
	public MovementHelper(Movement m) {
		this.movement = m;
	}
	
	public abstract double cost();
	
	public Movement getMovement() {
		return movement;
	}
	
}
