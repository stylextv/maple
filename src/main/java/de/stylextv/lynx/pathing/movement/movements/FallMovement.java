package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.BreakHelper;
import net.minecraft.core.BlockPos;

public class FallMovement extends Movement {
	
	private BreakHelper breakHelper = new BreakHelper();
	
	public FallMovement(Node source, Node destination) {
		super(source, destination);
		
		int x = destination.getX();
		int y = source.getY() + 1;
		int z = destination.getZ();
		
		breakHelper.collectBlock(x, y, z);
	}
	
	@Override
	public double cost() {
		boolean diagonal = isDiagonal();
		
		double cost = diagonal ? Cost.SPRINT_DIAGONALLY : Cost.SPRINT_STRAIGHT;
		
		cost /= 2;
		
		int dis = getSource().getY() - getDestination().getY();
		
		cost += Cost.FALL_N_BLOCKS[dis];
		
		cost += breakHelper.cost();
		
		return cost;
	}
	
	@Override
	public void onRenderTick() {
		if(breakHelper.hasBlocks()) {
			
			breakHelper.onRenderTick();
			
			return;
		}
		
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
