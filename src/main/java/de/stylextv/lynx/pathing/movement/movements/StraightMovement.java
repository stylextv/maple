package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.MovementState;

public class StraightMovement extends Movement {
	
	public StraightMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public void updateHelpers() {
		Node destination = getDestination();
		
		getBreakHelper().collectBlocks(destination, 2);
		
		BlockType type = destination.getType();
		
		if(type != BlockType.WATER) getPlaceHelper().collectBlock(destination, -1);
	}
	
	@Override
	public double cost() {
		double cost = getBreakHelper().cost();
		
		cost += getPlaceHelper().cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(getBreakHelper().onRenderTick()) return;
		
		if(!getPlaceHelper().onRenderTick()) {
			
			lookAt(getDestination());
			
			setPressed(InputAction.MOVE_FORWARD, true);
			setPressed(InputAction.SPRINT, true);
		}
	}
	
	@Override
	public MovementState getState() {
		if(getPlaceHelper().hasTargets()) return MovementState.PROCEEDING;
		
		return super.getState();
	}
	
}
