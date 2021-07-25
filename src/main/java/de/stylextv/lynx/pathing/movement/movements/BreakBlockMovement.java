package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.controller.BreakController;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.MovementState;
import net.minecraft.util.math.BlockPos;

public class BreakBlockMovement extends Movement {
	
	private BlockPos pos;
	
	private boolean started;
	
	public BreakBlockMovement(Node n, BlockPos pos) {
		super(n);
		
		this.pos = pos;
	}
	
	@Override
	public void onRenderTick() {
		if(started || !PlayerContext.isOnGround()) return;
		
		BreakController.breakBlock(pos);
		
		started = true;
	}
	
	@Override
	public MovementState getState() {
		if(!started) return MovementState.GOING;
		
		boolean b = BreakController.hasTarget();
		
		return b ? MovementState.GOING : MovementState.COMPLETED;
	}
	
}
