package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.ParkourHelper;
import net.minecraft.core.BlockPos;

public class ParkourMovement extends Movement {
	
	private ParkourHelper parkourHelper = new ParkourHelper(this);
	
	public ParkourMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public double cost() {
		double cost = parkourHelper.cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		lookAt(getDestination());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		
		int dis = horizontalDistance();
		
		if(dis > 3) setPressed(InputAction.SPRINT, true);
		
		BlockPos pos = getSource().blockPos();
		
		int dx = getDirectionX();
		int dy = getDirectionY();
		int dz = getDirectionZ();
		
		pos = pos.offset(dx, dy, dz);
		
		BlockPos feet = PlayerContext.feetPosition();
		
		if(feet.equals(pos)) setPressed(InputAction.JUMP, true);
	}
	
}
