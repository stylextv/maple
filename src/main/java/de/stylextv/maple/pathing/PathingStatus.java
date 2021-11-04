package de.stylextv.maple.pathing;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.calc.Path;
import de.stylextv.maple.pathing.calc.PathState;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.movement.MovementExecutor;
import net.minecraft.util.math.BlockPos;

public class PathingStatus {
	
	private PathingState state = PathingState.IDLE;
	
	private Goal goal;
	
	public void clear() {
		setGoal(null);
	}
	
	public void setGoal(Goal g) {
		goal = g;
		
		boolean b = g == null;
		
		state = b ? PathingState.IDLE : PathingState.GOING;
	}
	
	public boolean destinationMatches(Goal g) {
		Node destination = getDestination();
		
		return g.isFinalNode(destination);
	}
	
	public Node getDestination() {
		Path path = MovementExecutor.getPath();
		
		if(path != null) return path.lastNode();
		
		BlockPos pos = PlayerContext.feetPosition();
		
		return new Node(pos);
	}
	
	public boolean goalMatches(Goal g) {
		if(goal == null) return false;
		
		return goal.equals(g);
	}
	
	public boolean hasFoundGoal() {
		Path path = MovementExecutor.getPath();
		
		if(path == null) return isAtGoal();
		
		PathState state = path.getState();
		
		return state == PathState.FOUND_GOAL;
	}
	
	public boolean isIdle() {
		return !isPathing() && !isAtGoal();
	}
	
	public boolean isPathing() {
		return state == PathingState.GOING;
	}
	
	public boolean isAtGoal() {
		return state == PathingState.AT_GOAL;
	}
	
	public PathingState getState() {
		return state;
	}
	
	public void setState(PathingState state) {
		this.state = state;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
}
