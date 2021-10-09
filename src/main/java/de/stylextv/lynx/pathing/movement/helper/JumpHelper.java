package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.controller.AwarenessController;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
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
