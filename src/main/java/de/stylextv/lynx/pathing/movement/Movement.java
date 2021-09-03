package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.helper.BreakHelper;
import de.stylextv.lynx.pathing.movement.helper.BumpHelper;
import de.stylextv.lynx.pathing.movement.helper.DangerHelper;
import de.stylextv.lynx.pathing.movement.helper.PlaceHelper;
import de.stylextv.lynx.pathing.movement.helper.SpeedHelper;
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
	
	private SpeedHelper speedHelper = new SpeedHelper(this);
	
	private BumpHelper bumpHelper = new BumpHelper(this);
	
	private BreakHelper breakHelper = new BreakHelper(this);
	private PlaceHelper placeHelper = new PlaceHelper(this);
	
	private DangerHelper dangerHelper = new DangerHelper(this);
	
	public Movement(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
		
		updateHelpers();
	}
	
	public void updateHelpers() {}
	
	public double cost() {
		return speedHelper.cost() + bumpHelper.cost() + dangerHelper.cost();
	}
	
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
		
		return n.equals(pos) ? MovementState.DONE : MovementState.GOING;
	}
	
	public double distanceSqr(BlockPos pos) {
		int dis1 = source.distanceSqr(pos);
		int dis2 = destination.distanceSqr(pos);
		
		return Math.min(dis1, dis2);
	}
	
	public boolean isImpossible() {
		return cost() >= Cost.INFINITY;
	}
	
	public boolean isVerticalOnly() {
		return source.getX() == destination.getX() && source.getZ() == destination.getZ();
	}
	
	public boolean isDownwards() {
		return destination.getY() < source.getY();
	}
	
	public boolean isDiagonal() {
		return source.getX() != destination.getX() && source.getZ() != destination.getZ();
	}
	
	public Node getSource() {
		return source;
	}
	
	public Node getDestination() {
		return destination;
	}
	
	public SpeedHelper getSpeedHelper() {
		return speedHelper;
	}
	
	public BumpHelper getBumpHelper() {
		return bumpHelper;
	}
	
	public BreakHelper getBreakHelper() {
		return breakHelper;
	}
	
	public PlaceHelper getPlaceHelper() {
		return placeHelper;
	}
	
	public DangerHelper getDangerHelper() {
		return dangerHelper;
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
