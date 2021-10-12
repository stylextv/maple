package de.stylextv.maple.pathing.movement.movements;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.MovementState;

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
		cost += getInteractHelper().cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		boolean interacting = getBreakHelper().onRenderTick() || getInteractHelper().onRenderTick();
		
		if(interacting) return;
		
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
