package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class DescendMovement extends Movement {
	
	public DescendMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public void updateHelpers() {
		int height = 3;
		
		if(isVerticalOnly()) height = 1;
		
		getBreakHelper().collectBlocks(getDestination(), height);
	}
	
	@Override
	public double cost() {
		double cost;
		
		if(isVerticalOnly()) {
			
			cost = Cost.FALL_N_BLOCKS[1];
			
		} else {
			
			boolean diagonal = isDiagonal();
			
			cost = diagonal ? Cost.SPRINT_DIAGONALLY : Cost.SPRINT_STRAIGHT;
		}
		
		cost += getBreakHelper().cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(getBreakHelper().onRenderTick()) return;
		
		lookAt(getDestination());
		
		if(isVerticalOnly() && !PlayerContext.isOnGround()) return;
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
	}
	
}
