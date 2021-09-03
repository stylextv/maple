package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import net.minecraft.core.BlockPos;

public class FallMovement extends Movement {
	
	public FallMovement(Node source, Node destination) {
		super(source, destination);
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
		int dis = getSource().getY() - getDestination().getY();
		
		double cost = Cost.FALL_N_BLOCKS[dis];
		
		cost += getBreakHelper().cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(getBreakHelper().onRenderTick()) return;
		
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
	
}
