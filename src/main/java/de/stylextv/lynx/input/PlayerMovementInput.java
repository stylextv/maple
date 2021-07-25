package de.stylextv.lynx.input;

import de.stylextv.lynx.input.controller.InputController;
import net.minecraft.client.player.Input;

public class PlayerMovementInput extends Input {
	
	@Override
	public void tick(boolean b) {
		up = InputController.isPressed(InputAction.MOVE_FORWARD);
		down = InputController.isPressed(InputAction.MOVE_BACK);
		left = InputController.isPressed(InputAction.MOVE_LEFT);
		right = InputController.isPressed(InputAction.MOVE_RIGHT);
		
		forwardImpulse = up == down ? 0 : (up ? 1 : -1);
		leftImpulse = left == right ? 0 : (left ? 1 : -1);
		
		if(b) {
			forwardImpulse *= 0.3f;
			leftImpulse *= 0.3f;
		}
		
		jumping = InputController.isPressed(InputAction.JUMP);
		
		shiftKeyDown = InputController.isPressed(InputAction.SNEAK);
	}
	
}
