package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.event.events.RenderWorldEvent;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.favoring.Favoring;
import de.stylextv.lynx.pathing.movement.helper.BreakHelper;
import de.stylextv.lynx.pathing.movement.helper.BumpHelper;
import de.stylextv.lynx.pathing.movement.helper.DangerHelper;
import de.stylextv.lynx.pathing.movement.helper.PlaceHelper;
import de.stylextv.lynx.pathing.movement.helper.StepHelper;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.scheme.Colors;
import de.stylextv.lynx.util.time.TimeUtil;
import net.minecraft.util.math.BlockPos;

public abstract class Movement {
	
	private static final long EXECUTION_TIME_BUFFER = 4000;
	
	private static final double MAX_COST_INCREASE = 10;
	
	private Node source;
	private Node destination;
	
	private StepHelper stepHelper = new StepHelper(this);
	
	private BumpHelper bumpHelper = new BumpHelper(this);
	
	private BreakHelper breakHelper = new BreakHelper(this);
	private PlaceHelper placeHelper = new PlaceHelper(this);
	
	private DangerHelper dangerHelper = new DangerHelper(this);
	
	private double initialCost;
	
	private long startTime;
	
	public Movement(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
		
		updateHelpers();
	}
	
	public void updateHelpers() {}
	
	public double favoredCost() {
		return favoredCost(Favoring.getDefault());
	}
	
	public double favoredCost(Favoring favoring) {
		double d = favoring.getCoefficient(destination);
		
		double cost = cost() * d;
		
		if(initialCost == 0) initialCost = cost;
		
		return cost;
	}
	
	public double cost() {
		return stepHelper.cost() + bumpHelper.cost() + dangerHelper.cost();
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
		long now = System.currentTimeMillis();
		
		if(startTime == 0) {
			
			startTime = now;
			
		} else {
			
			long dt = now - startTime;
			
			double cost = favoredCost();
			
			long allowedTime = TimeUtil.ticksToMS(cost) + EXECUTION_TIME_BUFFER;
			
			if(dt > allowedTime) return MovementState.FAILED;
		}
		
		BlockPos pos = PlayerContext.feetPosition();
		
		Node n = getDestination();
		
		return n.equals(pos) ? MovementState.DONE : MovementState.PROCEEDING;
	}
	
	public void render(RenderWorldEvent event) {
		ShapeRenderer.drawNodeChain(event, destination, 2, Colors.PATH, 2);
		
		breakHelper.render(event);
		placeHelper.render(event);
	}
	
	public double squaredDistanceTo(BlockPos pos) {
		int dis1 = source.squaredDistanceTo(pos);
		int dis2 = destination.squaredDistanceTo(pos);
		
		return Math.min(dis1, dis2);
	}
	
	public boolean isImpossible() {
		double d = favoredCost() - initialCost;
		
		return d > MAX_COST_INCREASE;
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
	
	public StepHelper getStepHelper() {
		return stepHelper;
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
	
}
