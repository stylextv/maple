package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class PillarMovement extends Movement {
	
	public PillarMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	// TODO breaking/placing cost
	@Override
	public double cost() {
		return Cost.JUMP;
	}
	
	@Override
	public void onRenderTick() {
		lookAt(getSource(), true);
		
		setPressed(InputAction.JUMP, true);
	}
	
}
