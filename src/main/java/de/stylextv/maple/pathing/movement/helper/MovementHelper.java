package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import net.minecraft.util.math.BlockPos;

public class MovementHelper<T extends Movement> {
	
	private T movement;
	
	public MovementHelper(T m) {
		this.movement = m;
	}
	
	public double cost() {
		return 0;
	}
	
	public BlockPos sourcePos() {
		return getSource().blockPos();
	}
	
	public BlockPos destinationPos() {
		return getDestination().blockPos();
	}
	
	public Node getSource() {
		return movement.getSource();
	}
	
	public Node getDestination() {
		return movement.getDestination();
	}
	
	public T getMovement() {
		return movement;
	}
	
}
