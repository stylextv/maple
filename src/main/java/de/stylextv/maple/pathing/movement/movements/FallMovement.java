package de.stylextv.maple.pathing.movement.movements;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.MovementState;
import de.stylextv.maple.pathing.movement.helper.FallHelper;
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
		
		getInteractHelper().collectDefaultBlocks();
		getInteractHelper().collectBlocks(x, y, z, -1, 3);
	}
	
	@Override
	public double cost() {
		double cost = Cost.FALL_N_BLOCKS[fallDistance];
		
		cost += getBreakHelper().cost();
		cost += getInteractHelper().cost();
		
		cost += fallHelper.cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		boolean interacting = getBreakHelper().onRenderTick() || getInteractHelper().onRenderTick();
		
		if(interacting) return;
		
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
