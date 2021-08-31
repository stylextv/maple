package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.util.chat.ChatUtil;

public class MovementExecutor {
	
	private static Path path;
	
	public static void followPath(Path p) {
		stop();
		
		path = p;
	}
	
	public static void stop() {
		path = null;
		
		clearInputs();
	}
	
	private static void clearInputs() {
		InputController.releaseAll();
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
		
		m.updateHelpers();
		
		m.onRenderTick();
		
		if(PlayerContext.isInWater()) InputController.setPressed(InputAction.JUMP, true);
		
		MovementState state = m.getState();
		
		if(state != MovementState.GOING) {
			clearInputs();
			
			path.next();
		}
	}
	
	public static boolean hasPath() {
		return path != null;
	}
	
	public static Path getPath() {
		return path;
	}
	
}
