package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.Input;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import net.minecraft.util.math.BlockPos;

public class FallMovement extends Movement {
	
	public FallMovement(Node n) {
		super(n);
	}
	
	@Override
	public void onRenderTick() {
		lookAt(getNode(), true);
		
		BlockPos pos = PlayerContext.blockPosition();
		
		Node n = getNode();
		
		int x = pos.getX();
		int z = pos.getZ();
		
		boolean aboveDestination = x == n.getX() && z == n.getZ();
		
		boolean onGround = PlayerContext.isOnGround();
		
		setPressed(Input.MOVE_FORWARD, !aboveDestination || onGround);
		setPressed(Input.SPRINT, true);
	}
	
}
