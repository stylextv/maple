package de.stylextv.lynx.input;

import de.stylextv.lynx.input.controller.InputController;
import net.minecraft.client.input.Input;

public class InjectedInput extends Input {
	
	@Override
	public void tick(boolean slowDown) {
		pressingForward = InputController.isPressed(InputAction.MOVE_FORWARD);
		pressingBack = InputController.isPressed(InputAction.MOVE_BACK);
		pressingLeft = InputController.isPressed(InputAction.MOVE_LEFT);
		pressingRight = InputController.isPressed(InputAction.MOVE_RIGHT);
		
		movementForward = pressingForward == pressingBack ? 0 : (pressingForward ? 1 : -1);
		movementSideways = pressingLeft == pressingRight ? 0 : (pressingLeft ? 1 : -1);
		
		if(slowDown) {
			movementForward *= 0.3f;
			movementSideways *= 0.3f;
		}
		
		jumping = InputController.isPressed(InputAction.JUMP);
		
		sneaking = InputController.isPressed(InputAction.SNEAK);
		
		InputController.releaseAll();
	}
	
}
