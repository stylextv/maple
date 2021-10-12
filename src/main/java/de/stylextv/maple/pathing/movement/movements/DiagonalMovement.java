package de.stylextv.maple.pathing.movement.movements;

import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.helper.InteractHelper;

public class DiagonalMovement extends Movement {
	
	private InteractHelper interactHelper = new InteractHelper(this);
	
	public DiagonalMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public double cost() {
		double cost = interactHelper.cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(interactHelper.onRenderTick()) return;
		
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
	}
	
}
