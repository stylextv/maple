package de.stylextv.lynx.input.controller;

import java.util.HashSet;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.MouseButton;
import de.stylextv.lynx.input.PlayerMovementInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;

public class InputController {
	
	private static HashSet<InputAction> pressedInputs = new HashSet<>();
	
	public static void setPressed(InputAction i, boolean pressed) {
		if(pressed) pressedInputs.add(i);
		else pressedInputs.remove(i);
	}
	
	public static boolean isPressed(InputAction i) {
		return pressedInputs.contains(i);
	}
	
	public static void releaseAll() {
		pressedInputs.clear();
	}
	
	public static void onTick() {
		MouseButton.updateAll();
		
		LocalPlayer p = PlayerContext.player();
		
		boolean injected = p.input.getClass() == PlayerMovementInput.class;
		
		if(isInControl()) {
			
			p.setSprinting(isMoving() && isPressed(InputAction.SPRINT));
			
			PlayerContext.setFlying(false);
			
			if(!injected) {
				p.input = new PlayerMovementInput();
			}
			
		} else {
			
			if(injected) {
				p.input = new KeyboardInput(GameContext.settings());
			}
		}
	}
	
	public static boolean isMoving() {
		for(InputAction i : pressedInputs) {
			if(i.isMove()) return true;
		}
		
		return false;
	}
	
	public static boolean isInControl() {
		return !pressedInputs.isEmpty();
	}
	
}
