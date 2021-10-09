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
		
		double dx = sourceX - x;
		double dz = sourceZ - z;
		
		double forwards = dx;
		double sideways = dz;
		
		if(dirX == 0) {
			
			forwards = dz;
			sideways = dx;
		}
		
		if(forwards > 0.79) return true;
		
		Node destination = m.getDestination();
		
		ViewController.lookAt(destination, false);
		
		return false;
	}
	
}
