package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.movements.ParkourMovement;
import net.minecraft.util.math.Vec3d;

public class PositionHelper extends MovementHelper<Movement> {
	
	private static final double MAX_DISTANCE_FROM_CENTER = 0.016;
	
	private static final double JUMP_OFFSET = 0.79;
	
	private static final double RUNUP_OFFSET = -0.4;
	
	private static final double MIN_SIDEWAYS_DISTANCE = 0.2;
	private static final double MIN_SIDEWAYS_SPEED = 0.05;
	
	public PositionHelper(Movement m) {
		super(m);
	}
	
	public boolean centerOnSource() {
		Movement m = getMovement();
		
		Node source = m.getSource();
		
		double sourceX = source.getX() + 0.5;
		double sourceZ = source.getZ() + 0.5;
		
		double dis = PlayerContext.squaredDistanceTo(sourceX, sourceZ);
		
		boolean atCenter = dis < MAX_DISTANCE_FROM_CENTER;
		
		if(atCenter) {
			
			double speed = PlayerContext.horizontalSpeed();
			
			return speed == 0;
		}
		
		InputController.setPressed(InputAction.MOVE_FORWARD, true);
		InputController.setPressed(InputAction.SNEAK, true);
		
		ViewController.lookAt(source, false);
		
		return false;
	}
	
	public boolean prepareParkourJump() {
		ParkourMovement m = (ParkourMovement) getMovement();
		
		Node source = m.getSource();
		
		double sourceX = source.getX() + 0.5;
		double sourceZ = source.getZ() + 0.5;
		
		int dirX = m.getDirectionX();
		int dirZ = m.getDirectionZ();
		
		Vec3d v = PlayerContext.position();
		
		double x = v.getX();
		double z = v.getZ();
		
		double dx = x - sourceX;
		double dz = z - sourceZ;
		
		double forwards = dx * dirX;
		double sideways = dz;
		
		Vec3d velocity = PlayerContext.velocity();
		
		double forwardsVelocity = velocity.getX() * dirX;
		double sidewaysVelocity = velocity.getZ();
		
		if(dirX == 0) {
			
			forwards = dz * dirZ;
			sideways = dx;
			
			forwardsVelocity = velocity.getZ() * dirZ;
			sidewaysVelocity = velocity.getX();
		}
		
		double sidewaysDis = Math.abs(sideways);
		
		boolean positionAligned = sidewaysDis < MIN_SIDEWAYS_DISTANCE;
		
		boolean inRunup = forwards > RUNUP_OFFSET;
		
		double sidewaysSpeed = Math.abs(sidewaysVelocity);
		
		boolean forwardsVelocityAligned = !inRunup || forwardsVelocity > 0;
		boolean sidewaysVelocityAligned = sidewaysSpeed < MIN_SIDEWAYS_SPEED;
		
		boolean aligned = forwardsVelocityAligned && sidewaysVelocityAligned && positionAligned;
		
		if(aligned) {
			
			if(forwards > JUMP_OFFSET) return true;
			
			Node destination = m.getDestination();
			
			ViewController.lookAt(destination, false);
			
			InputController.setPressed(InputAction.MOVE_FORWARD, true);
			
			return false;
		}
		
		double targetX = sourceX - dirX * 0.5;
		double targetZ = sourceZ - dirZ * 0.5;
		
		ViewController.lookAt(targetX, targetZ);
		
		InputController.setPressed(InputAction.MOVE_FORWARD, !positionAligned || !forwardsVelocityAligned);
		InputController.setPressed(InputAction.SNEAK, true);
		
		return false;
	}
	
}
