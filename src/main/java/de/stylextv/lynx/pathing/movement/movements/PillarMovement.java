package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.AwarenessController;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.MovementState;
import de.stylextv.lynx.pathing.movement.helper.PositionHelper;

public class PillarMovement extends Movement {
	
	private PositionHelper positionHelper = new PositionHelper(this);
	
	public PillarMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public void updateHelpers() {
		getBreakHelper().collectBlocks(getSource(), 2, 1);
		
		getPlaceHelper().collectBlock(getSource());
	}
	
	@Override
	public double cost() {
		double cost = Cost.JUMP;
		
		cost += getBreakHelper().cost();
		
		if(getPlaceHelper().cost() >= Cost.INFINITY) return Cost.INFINITY;
		
		cost += Cost.placeCost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(getBreakHelper().onRenderTick()) return;
		
		if(!positionHelper.centerOnSource()) return;
		
		getPlaceHelper().onRenderTick();
		
		boolean jump = AwarenessController.canJump();
		
		setPressed(InputAction.JUMP, jump);
	}
	
	@Override
	public MovementState getState() {
		if(!PlayerContext.isOnGround()) return MovementState.PROCEEDING;
		
		return super.getState();
	}
	
}
