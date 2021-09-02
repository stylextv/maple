package de.stylextv.lynx.input;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.input.controller.InputController;
import net.minecraft.client.KeyMapping;

public class MouseButton {
	
	public static final MouseButton LEFT_CLICK = new MouseButton(InputAction.LEFT_CLICK, GameContext.settings().keyAttack);
	public static final MouseButton RIGHT_CLICK = new MouseButton(InputAction.RIGHT_CLICK, GameContext.settings().keyUse);
	
	private InputAction action;
	
	private KeyMapping keyBind;
	
	private boolean pressed;
	
	public MouseButton(InputAction i, KeyMapping keyBind) {
		this.action = i;
		this.keyBind = keyBind;
	}
	
	public void update() {
		boolean b = InputController.isPressed(action);
		
		if(b) {
			
			keyBind.setDown(true);
			
		} else if(pressed) {
			
			keyBind.setDown(false);
		}
		
		pressed = b;
	}
	
	public InputAction getAction() {
		return action;
	}
	
	public KeyMapping getKeyBind() {
		return keyBind;
	}
	
	public static void updateAll() {
		LEFT_CLICK.update();
		RIGHT_CLICK.update();
	}
	
}
