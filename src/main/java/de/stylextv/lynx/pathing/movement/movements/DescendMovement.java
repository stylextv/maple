package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.BreakHelper;

public class DescendMovement extends Movement {
	
	private BreakHelper breakHelper = new BreakHelper();
	
	public DescendMovement(Node source, Node destination) {
		super(source, destination);
		
		int height = 3;
		
		if(isVerticalOnly()) height = 1;
		
		breakHelper.collectBlocks(destination, height);
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
		
		cost += breakHelper.cost();
		
		return cost;
	}
	
	@Override
	public void onRenderTick() {
		if(breakHelper.hasBlocks()) {
			
			breakHelper.onRenderTick();
			
			return;
		}
		
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
	}
	
}
