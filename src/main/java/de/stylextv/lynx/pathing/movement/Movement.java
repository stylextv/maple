package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.movements.AscendMovement;
import de.stylextv.lynx.pathing.movement.movements.DescendMovement;
import de.stylextv.lynx.pathing.movement.movements.DiagonalMovement;
import de.stylextv.lynx.pathing.movement.movements.FallMovement;
import de.stylextv.lynx.pathing.movement.movements.PillarMovement;
import de.stylextv.lynx.pathing.movement.movements.StraightMovement;
import net.minecraft.core.BlockPos;

public abstract class Movement {
	
	private Node source;
	private Node destination;
	
	public Movement(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public abstract double cost();
	
	public abstract void onRenderTick();
	
	protected void setPressed(InputAction i, boolean pressed) {
		InputController.setPressed(i, pressed);
	}
	
	protected void lookAt(BlockPos pos) {
		ViewController.lookAt(pos);
	}
	
	protected void lookAt(Node n) {
		ViewController.lookAt(n);
	}
	
	protected void lookAt(Node n, boolean lookDown) {
		ViewController.lookAt(n, lookDown);
	}
	
	public MovementState getState() {
		BlockPos pos = PlayerContext.feetPosition();
		
		Node n = getDestination();
		
		return n.equals(pos) ? MovementState.REACHED_NODE : MovementState.GOING;
	}
	
	public boolean isDiagonal() {
		int diffX = source.getX() - destination.getX();
		int diffZ = source.getZ() - destination.getZ();
		
		int dis = Math.abs(diffX) + Math.abs(diffZ);
		
		return dis > 1;
	}
	
	public Node getSource() {
		return source;
	}
	
	public Node getDestination() {
		return destination;
	}
	
	public static Movement fromNodes(Node n1, Node n2) {
		int x = n1.getX();
		int y = n1.getY();
		int z = n1.getZ();
		
		int disX = Math.abs(x - n2.getX());
		int disZ = Math.abs(z - n2.getZ());
		
		int dis = disX + disZ;
		
		if(y != n2.getY()) {
			
			boolean ascend = n2.getY() > y;
			
			if(ascend) {
				if(dis == 0) return new PillarMovement(n1, n2);
				
				return new AscendMovement(n1, n2);
			}
			
			int fallDistance = y - n2.getY();
			
			if(fallDistance == 1) return new DescendMovement(n1, n2);
			
			return new FallMovement(n1, n2);
			
		} else {
			
			boolean diagonally = dis > 1;
			
			if(diagonally) return new DiagonalMovement(n1, n2);
			
			return new StraightMovement(n1, n2);
		}
	}
	
}
