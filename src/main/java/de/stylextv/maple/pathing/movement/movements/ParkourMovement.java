package de.stylextv.maple.pathing.movement.movements;

import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.helper.ParkourHelper;

public class ParkourMovement extends Movement {
	
	private int dx;
	private int dy;
	private int dz;
	
	private int distance;
	
	private ParkourHelper parkourHelper = new ParkourHelper(this);
	
	public ParkourMovement(Node source, Node destination, int dx, int dy, int dz, int distance) {
		super(source, destination);
		
		this.dx = dx;
		this.dy = dy;
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
		
		boolean prepared = getPositionHelper().prepareParkourJump();
		
		if(!prepared) return;
		
		boolean jump = getJumpHelper().canJump();
		
		setPressed(InputAction.JUMP, jump);
	}
	
	public int getDeltaX() {
		return dx;
	}
	
	public int getDeltaY() {
		return dy;
	}
	
	public int getDeltaZ() {
		return dz;
	}
	
	public int getDistance() {
		return distance;
	}
	
}
