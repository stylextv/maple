package de.stylextv.lynx.input.controller;

import java.util.HashSet;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.Input;
import de.stylextv.lynx.input.PlayerMovementInput;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.MovementInputFromOptions;

public class InputController {
	
	private static HashSet<Input> pressedInputs = new HashSet<>();
	
	public static void setPressed(Input i, boolean pressed) {
		if(pressed) pressedInputs.add(i);
		else pressedInputs.remove(i);
	}
	
	public static boolean isPressed(Input i) {
		return pressedInputs.contains(i);
	}
	
	public static void releaseAll() {
		pressedInputs.clear();
	}
	
	public static void onTick() {
		// handle left and right clicks and unpress them
		
		ClientPlayerEntity p = PlayerContext.player();
		
		if(isInControl()) {
			
			p.setSprinting(isMoving() && isPressed(Input.SPRINT));
			
			PlayerContext.setFlying(false);
			
			if(p.input.getClass() != PlayerMovementInput.class) {
				p.input = new PlayerMovementInput();
			}
			
		} else {
			
			if(p.input.getClass() == PlayerMovementInput.class) {
				p.input = new MovementInputFromOptions(GameContext.settings());
			}
		}
	}
	
	public static boolean isMoving() {
		for(Input i : pressedInputs) {
			if(i.isMove()) return true;
		}
		
		return false;
	}
	
	public static boolean isInControl() {
		return !pressedInputs.isEmpty();
	}
	
}
