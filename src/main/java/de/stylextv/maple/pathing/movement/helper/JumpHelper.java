package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.input.controller.AwarenessController;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import net.minecraft.util.math.BlockPos;

public class JumpHelper extends MovementHelper<Movement> {
	
	public JumpHelper(Movement m) {
		super(m);
	}
	
	public boolean shouldJump() {
		if(!canJump()) return false;
		
		Movement m = getMovement();
		
		Node destination = m.getDestination();
		
		int destinationY = destination.getY();
		
		BlockPos pos = PlayerContext.feetPosition();
		
		int y = pos.getY();
		
		return y < destinationY;
	}
	
	public boolean canJump() {
		return AwarenessController.canJump();
	}
	
}
