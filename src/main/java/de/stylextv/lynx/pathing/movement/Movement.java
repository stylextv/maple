package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.PlayerContext;
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
import net.minecraft.core.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public abstract class Movement {
	
	private static final long EXECUTION_TIME_BUFFER = 3000;
	
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
			
			if(dt >= cost + EXECUTION_TIME_BUFFER) {
				
				return MovementState.FAILED;
			}
		}
		
		BlockPos pos = PlayerContext.feetPosition();
		
		Node n = getDestination();
		
		return n.equals(pos) ? MovementState.DONE : MovementState.GOING;
	}
	
	public void render(RenderWorldLastEvent event) {
		ShapeRenderer.drawNodeChain(event, destination, 2, Colors.PATH, 2);
		
		breakHelper.render(event);
		placeHelper.render(event);
	}
	
	public double distanceSqr(BlockPos pos) {
		int dis1 = source.distanceSqr(pos);
		int dis2 = destination.distanceSqr(pos);
		
		return Math.min(dis1, dis2);
	}
	
	public int horizontalDistance() {
		int disX = Math.abs(destination.getX() - source.getX());
		int disZ = Math.abs(destination.getZ() - source.getZ());
		
		return Math.max(disX, disZ);
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
	
	public int getDirectionX() {
		return Integer.compare(destination.getX(), source.getX());
	}
	
	public int getDirectionY() {
		return Integer.compare(destination.getY(), source.getY());
	}
	
	public int getDirectionZ() {
		return Integer.compare(destination.getZ(), source.getZ());
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
