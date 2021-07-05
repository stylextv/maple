package de.stylextv.lynx.input;

import de.stylextv.lynx.input.controller.InputController;
import net.minecraft.util.MovementInput;

public class PlayerMovementInput extends MovementInput {
	
	@Override
	public void tick(boolean b) {
		up = InputController.isPressed(Input.MOVE_FORWARD);
		down = InputController.isPressed(Input.MOVE_BACK);
		left = InputController.isPressed(Input.MOVE_LEFT);
		right = InputController.isPressed(Input.MOVE_RIGHT);
		
		forwardImpulse = up == down ? 0 : (up ? 1 : -1);
		leftImpulse = left == right ? 0 : (left ? 1 : -1);
		
		if(b) {
			forwardImpulse *= 0.3f;
			leftImpulse *= 0.3f;
		}
		
		jumping = InputController.isPressed(Input.JUMP);
		
		shiftKeyDown = InputController.isPressed(Input.SNEAK);
	}
	
}
