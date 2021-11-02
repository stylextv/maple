package de.stylextv.maple.pathing.movement.movements;

import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.helper.ParkourHelper;
import de.stylextv.maple.pathing.movement.helper.PositionHelper;

public class ParkourMovement extends Movement {
	
	private int dx;
	private int dz;
	
	private int distance;
	
	private ParkourHelper parkourHelper = new ParkourHelper(this);
	
	private PositionHelper positionHelper = new PositionHelper(this);
	
	public ParkourMovement(Node source, Node destination, int dx, int dz, int distance) {
		super(source, destination);
		
		this.dx = dx;
		this.dz = dz;
		this.distance = distance;
	}
	
	@Override
	public double cost() {
		double cost = parkourHelper.cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		boolean sprint = parkourHelper.shouldSprint();
		
		setPressed(InputAction.SPRINT, sprint);
		
		boolean prepared = positionHelper.prepareParkourJump();
		
		if(!prepared) return;
		
		boolean jump = getJumpHelper().canJump();
		
		setPressed(InputAction.JUMP, jump);
	}
	
	public int getDeltaX() {
		return dx;
	}
	
	public int getDeltaZ() {
		return dz;
	}
	
	public int getDistance() {
		return distance;
	}
	
}
