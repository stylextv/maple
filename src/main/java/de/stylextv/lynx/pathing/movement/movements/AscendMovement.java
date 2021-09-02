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
	public void updateHelpers() {
		getBreakHelper().collectBlocks(getDestination(), 2);
		getBreakHelper().collectBlocks(getSource(), 2, 1);
	}
	
	@Override
	public double cost() {
		boolean diagonal = isDiagonal();
		
		double cost = diagonal ? Cost.SPRINT_DIAGONALLY : Cost.SPRINT_STRAIGHT;
		
		cost += Cost.JUMP + getBreakHelper().cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(getBreakHelper().onRenderTick()) return;
		
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
		
		setPressed(InputAction.JUMP, true);
	}
	
}
