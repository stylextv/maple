package de.stylextv.maple.input.controller;

import java.util.HashSet;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.input.InjectedInput;
import de.stylextv.maple.input.InputAction;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;

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
		ClientPlayerEntity p = PlayerContext.player();
		
		boolean injected = p.input.getClass() == InjectedInput.class;
		
		if(isInControl()) {
			
			boolean isSneaking = isPressed(InputAction.SNEAK);
			
			p.setSprinting(!isSneaking && isMoving() && isPressed(InputAction.SPRINT));
			
			PlayerContext.setFlying(false);
			
			if(!injected) {
				p.input = new InjectedInput();
			}
			
		} else {
			
			if(injected) {
				p.input = new KeyboardInput(GameContext.options());
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
