package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.pathing.movement.Movement;

public class MovementHelper<T extends Movement> {
	
	private T movement;
	
	public MovementHelper(T m) {
		this.movement = m;
	}
	
	public double cost() {
		return 0;
	}
	
	public T getMovement() {
		return movement;
	}
	
}
