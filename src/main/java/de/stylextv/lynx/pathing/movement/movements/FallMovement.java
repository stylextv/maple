package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.MovementState;
import de.stylextv.lynx.pathing.movement.helper.FallHelper;
import net.minecraft.util.math.BlockPos;

public class FallMovement extends Movement {
	
	private int fallDistance;
	
	private FallHelper fallHelper = new FallHelper(this);
	
	public FallMovement(Node source, Node destination, int fallDistance) {
		super(source, destination);
		
		this.fallDistance = fallDistance;
	}
	
	@Override
	public void updateHelpers() {
		int x = getDestination().getX();
		int y = getSource().getY();
		int z = getDestination().getZ();
		
		getBreakHelper().collectBlocks(x, y, z, -1, 3);
	}
	
	@Override
	public double cost() {
		double cost = Cost.FALL_N_BLOCKS[fallDistance];
		
		cost += getBreakHelper().cost();
		
		cost += fallHelper.cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(getBreakHelper().onRenderTick()) return;
		
		fallHelper.onRenderTick();
		
		Node n = getDestination();
		
		lookAt(n, true);
		
		BlockPos pos = PlayerContext.blockPosition();
		
		int x = pos.getX();
		int z = pos.getZ();
		
		boolean aboveDestination = x == n.getX() && z == n.getZ();
		
		boolean onGround = PlayerContext.isOnGround();
		
		setPressed(InputAction.MOVE_FORWARD, !aboveDestination || onGround);
		setPressed(InputAction.SPRINT, true);
	}
	
	@Override
	public MovementState getState() {
		if(!fallHelper.isFinished()) return MovementState.PROCEEDING;
		
		return super.getState();
	}
	
	public int getFallDistance() {
		return fallDistance;
	}
	
}
