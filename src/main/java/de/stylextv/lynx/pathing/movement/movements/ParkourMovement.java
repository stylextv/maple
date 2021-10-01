package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.AwarenessController;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.ParkourHelper;
import net.minecraft.util.math.Vec3d;

public class ParkourMovement extends Movement {
	
	private static final double JUMP_OFFSET = 0.79;
	
	private int dx;
	private int dz;
	
	private int distance;
	
	private ParkourHelper parkourHelper = new ParkourHelper(this);
	
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
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		
		if(distance > 3) setPressed(InputAction.SPRINT, true);
		
		boolean jump = false;
		
		if(AwarenessController.canJump()) {
			
			Node n = getSource();
			
			Vec3d v = PlayerContext.position();
			
			double disToJump;
			
			if(dz == 0) {
				
				double x = n.getX() + 0.5 + dx * JUMP_OFFSET;
				
				disToJump = (x - v.getX()) * dx;
				
			} else {
				
				double z = n.getZ() + 0.5 + dz * JUMP_OFFSET;
				
				disToJump = (z - v.getZ()) * dz;
			}
			
			jump = disToJump < 0 && disToJump > -0.5;
		}
		
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
