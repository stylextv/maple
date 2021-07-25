package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.controller.PlaceController;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.MovementState;
import net.minecraft.core.BlockPos;

public class PlaceBlockMovement extends Movement {
	
	private BlockPos pos;
	private BlockPos supportPos;
	
	private boolean started;
	
	public PlaceBlockMovement(Node n, BlockPos pos, BlockPos supportPos) {
		super(n);
		
		this.pos = pos;
		this.supportPos = supportPos;
	}
	
	@Override
	public void onRenderTick() {
		if(started || !PlayerContext.isOnGround()) return;
		
		PlaceController.placeBlock(pos, supportPos);
		
		started = true;
	}
	
	@Override
	public MovementState getState() {
		if(!started) return MovementState.GOING;
		
		boolean b = PlaceController.hasTarget();
		
		return b ? MovementState.GOING : MovementState.REACHED_NODE;
	}
	
}
