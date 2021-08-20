package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class AscendMovement extends Movement {
	
	public AscendMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public double cost() {
		boolean diagonal = isDiagonal();
		
		double cost = diagonal ? Cost.SPRINT_DIAGONALLY : Cost.SPRINT_STRAIGHT;
		
		cost += Cost.JUMP;
		
		// TODO breaking cost
		
		return cost;
	}
	
	@Override
	public void onRenderTick() {
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
		
		setPressed(InputAction.JUMP, true);
	}
	
}
