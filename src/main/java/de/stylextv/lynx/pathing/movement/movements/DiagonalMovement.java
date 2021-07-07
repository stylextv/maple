package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.Input;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class DiagonalMovement extends Movement {
	
	public DiagonalMovement(Node n) {
		super(n);
	}
	
	@Override
	public void onRenderTick() {
		lookAt(getNode());
		
		setPressed(Input.MOVE_FORWARD, true);
		setPressed(Input.SPRINT, true);
	}
	
}
