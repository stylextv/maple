package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.MovementState;
import de.stylextv.lynx.pathing.movement.helper.BreakHelper;
import de.stylextv.lynx.pathing.movement.helper.PlaceHelper;

public class PillarMovement extends Movement {
	
	private BreakHelper breakHelper = new BreakHelper();
	
	private PlaceHelper placeHelper = new PlaceHelper();
	
	public PillarMovement(Node source, Node destination) {
		super(source, destination);
		
		breakHelper.collectBlocks(source, 2, 1);
		
		placeHelper.collectBlock(source);
	}
	
	@Override
	public double cost() {
		double cost = Cost.JUMP;
		
		cost += breakHelper.cost();
		cost += placeHelper.cost();
		
		return cost;
	}
	
	@Override
	public void onRenderTick() {
		if(breakHelper.hasTargets()) {
			
			breakHelper.onRenderTick();
			
			return;
		}
		
		if(placeHelper.hasTargets()) placeHelper.onRenderTick();
		
		setPressed(InputAction.JUMP, true);
	}
	
	@Override
	public MovementState getState() {
		if(placeHelper.hasTargets()) return MovementState.GOING;
		
		return super.getState();
	}
	
}
