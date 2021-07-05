package de.stylextv.dwarf.input.controller;

import java.util.HashSet;

import de.stylextv.dwarf.input.Input;
import de.stylextv.dwarf.input.PlayerContext;
import de.stylextv.dwarf.input.PlayerMovementInput;
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
			
			p.abilities.flying = false;
			
			if(p.input.getClass() != PlayerMovementInput.class) {
				p.input = new PlayerMovementInput();
			}
			
		} else {
			
			if(p.input.getClass() == PlayerMovementInput.class) {
				p.input = new MovementInputFromOptions(PlayerContext.gameSettings());
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
