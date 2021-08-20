package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class StraightMovement extends Movement {
	
	public StraightMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	// TODO breaking/placing cost
	@Override
	public double cost() {
		return Cost.SPRINT_STRAIGHT;
	}
	
	@Override
	public void onRenderTick() {
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
	}
	
}
