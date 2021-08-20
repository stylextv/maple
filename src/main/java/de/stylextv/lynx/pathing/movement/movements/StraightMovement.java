package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.BreakHelper;
import de.stylextv.lynx.pathing.movement.helper.PlaceHelper;

public class StraightMovement extends Movement {
	
	private BreakHelper breakHelper = new BreakHelper();
	
	private PlaceHelper placeHelper = new PlaceHelper();
	
	public StraightMovement(Node source, Node destination) {
		super(source, destination);
		
		breakHelper.collectBlocks(destination, 2);
		
		placeHelper.collectBlock(destination, -1);
	}
	
	@Override
	public double cost() {
		double cost = Cost.SPRINT_STRAIGHT;
		
		cost += breakHelper.cost();
		cost += placeHelper.cost();
		
		return cost;
	}
	
	@Override
	public void onRenderTick() {
		if(breakHelper.hasBlocks()) {
			
			breakHelper.onRenderTick();
			
			return;
		}
		
		if(placeHelper.hasBlocks()) placeHelper.onRenderTick();
		
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
	}
	
}
