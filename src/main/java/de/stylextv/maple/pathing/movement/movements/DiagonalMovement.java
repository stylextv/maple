package de.stylextv.maple.pathing.movement.movements;

import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;

public class DiagonalMovement extends Movement {
	
	public DiagonalMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public double cost() {
		double cost = getInteractHelper().cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(getInteractHelper().onRenderTick()) return;
		
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
	}
	
}
