package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.ParkourHelper;

public class ParkourMovement extends Movement {
	
	private ParkourHelper parkourHelper = new ParkourHelper(this);
	
	public ParkourMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public double cost() {
		double cost = parkourHelper.cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		lookAt(getDestination());
		
		int dis = horizontalDistance();
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
		
		setPressed(InputAction.JUMP, true);
	}
	
}
