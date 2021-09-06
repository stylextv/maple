package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.helper.ParkourHelper;
import net.minecraft.world.phys.Vec3;

public class ParkourMovement extends Movement {
	
	private static final double JUMP_OFFSET = 0.79;
	
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
		
		Node n = getSource();
		
		Vec3 v = PlayerContext.position();
		
		int dx = getDirectionX();
		int dz = getDirectionZ();
		
		double disToJump;
		
		if(dz == 0) {
			
			double x = n.getX() + 0.5 + dx * JUMP_OFFSET;
			
			disToJump = (x - v.x()) * dx;
			
		} else {
			
			double z = n.getZ() + 0.5 + dz * JUMP_OFFSET;
			
			disToJump = (z - v.z()) * dz;
		}
		
		boolean jump = disToJump < 0 && disToJump > -0.5;
		
		setPressed(InputAction.JUMP, jump);
	}
	
}
