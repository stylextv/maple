package de.stylextv.maple.pathing.movement;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.input.controller.InputController;
import de.stylextv.maple.pathing.PathingExecutor;
import de.stylextv.maple.pathing.PathingState;
import de.stylextv.maple.pathing.PathingStatus;
import de.stylextv.maple.pathing.calc.Path;
import de.stylextv.maple.pathing.calc.PathState;
import net.minecraft.util.math.BlockPos;

public class MovementExecutor {
	
	private static final int MAX_DISTANCE_TO_PATH = 9;
	
	private static Path path;
	
	private static boolean paused;
	
	public static void followPath(Path p) {
		stop();
		
		path = p;
	}
	
	public static void stop() {
		path = null;
	}
	
	public static void recalc() {
		path.clear();
	}
	
	public static void onRenderTick() {
		if(path == null) return;
		
		Movement m = path.getCurrentMovement();
		
		if(m == null) {
			
			if(path.isFinished()) {
				
				boolean atGoal = path.getState() == PathState.AT_GOAL;
				
				PathingStatus status = PathingExecutor.getStatus();
				
				PathingState state = atGoal ? PathingState.AT_GOAL : PathingState.FAILED;
				
				status.setState(state);
				
				stop();
			}
			
			return;
		}
		
		if(needsToRecalc()) {
			
			recalc();
			
			return;
		}
		
		m.updateHelpers();
		
		if(!paused) {
			
			m.onRenderTick();
			
			double dt = GameContext.lastFrameDuration();
			
			m.tick(dt);
		}
		
		if(PlayerContext.isInWater()) {
			
			InputController.setPressed(InputAction.JUMP, true);
			InputController.setPressed(InputAction.SNEAK, false);
		}
		
		MovementState state = m.getState();
		
		if(state == MovementState.FAILED) {
			
			recalc();
			
		} else if(state == MovementState.DONE) {
			
			path.next();
		}
	}
	
	private static boolean needsToRecalc() {
		Movement m = path.getCurrentMovement();
		
		if(m.ranOutOfTime()) return true;
		
		if(!PlayerContext.isFalling() && !PlayerContext.isInWater()) {
			
			BlockPos pos = PlayerContext.blockPosition();
			
			double dis = path.distanceSqr(pos);
			
			if(dis > MAX_DISTANCE_TO_PATH) return true;
		}
		
		return path.isImpossible();
	}
	
	public static boolean hasPath() {
		return path != null;
	}
	
	public static Path getPath() {
		return path;
	}
	
	public static boolean isPaused() {
		return paused;
	}
	
	public static void setPaused(boolean b) {
		paused = b;
	}
	
}
