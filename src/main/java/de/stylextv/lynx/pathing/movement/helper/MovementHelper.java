package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.pathing.movement.Movement;

public abstract class MovementHelper<T extends Movement> {
	
	private T movement;
	
	public MovementHelper(T m) {
		this.movement = m;
	}
	
	public abstract double cost();
	
	public T getMovement() {
		return movement;
	}
	
}
