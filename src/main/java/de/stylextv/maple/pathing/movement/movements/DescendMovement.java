package de.stylextv.maple.pathing.movement.movements;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;

public class DescendMovement extends Movement {
	
	public DescendMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public void updateHelpers() {
		int height = 3;
		
		if(isVerticalOnly()) height = 1;
		
		getBreakHelper().collectBlocks(getDestination(), height);
		
		getInteractHelper().collectDefaultBlocks();
	}
	
	@Override
	public double cost() {
		double cost = isVerticalOnly() ? Cost.FALL_N_BLOCKS[1] : 0;
		
		cost += getBreakHelper().cost();
		cost += getInteractHelper().cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		boolean interacting = getBreakHelper().onRenderTick() || getInteractHelper().onRenderTick();
		
		if(interacting) return;
		
		boolean lookDown = isVerticalOnly();
		
		lookAt(getDestination(), lookDown);
		
		if(isVerticalOnly() && !PlayerContext.isOnGround()) return;
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
	}
	
}
