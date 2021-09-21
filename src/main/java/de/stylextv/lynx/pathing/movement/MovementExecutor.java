package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.util.chat.ChatUtil;
import net.minecraft.util.math.BlockPos;

public class MovementExecutor {
	
	private static final int MAX_DISTANCE_TO_PATH = 9;
	
	private static Path path;
	
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
				ChatUtil.send("Destination reached.");
				
				stop();
			}
			
			return;
		}
		
		if(needsToRecalc()) {
			
			recalc();
			
			return;
		}
		
		m.updateHelpers();
		
		m.onRenderTick();
		
		double dt = GameContext.lastFrameDuration();
		
		m.tick(dt);
		
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
		
		if(!PlayerContext.isFalling()) {
			
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
	
}
