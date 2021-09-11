package de.stylextv.lynx.input;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.input.controller.InputController;
import net.minecraft.client.option.KeyBinding;

public class MouseButton {
	
	public static final MouseButton LEFT_CLICK = new MouseButton(InputAction.LEFT_CLICK, GameContext.options().keyAttack);
	public static final MouseButton RIGHT_CLICK = new MouseButton(InputAction.RIGHT_CLICK, GameContext.options().keyUse);
	
	private InputAction action;
	
	private KeyBinding binding;
	
	private boolean pressed;
	
	public MouseButton(InputAction i, KeyBinding binding) {
		this.action = i;
		this.binding = binding;
	}
	
	public void update() {
		boolean b = InputController.isPressed(action);
		
		if(b) {
			
			binding.setPressed(true);
			
		} else if(pressed) {
			
			binding.setPressed(false);
		}
		
		pressed = b;
	}
	
	public InputAction getAction() {
		return action;
	}
	
	public KeyBinding getKeyBind() {
		return binding;
	}
	
	public static void updateAll() {
		LEFT_CLICK.update();
		RIGHT_CLICK.update();
	}
	
}
