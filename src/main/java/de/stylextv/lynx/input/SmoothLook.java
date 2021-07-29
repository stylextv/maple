package de.stylextv.lynx.input;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.client.player.LocalPlayer;

public class SmoothLook {
	
	private static final float TURN_SPEED = 6f;
	
	private boolean active;
	
	private double turnYawAmount;
	private double turnPitchAmount;
	
	public void feedInput(double turnYaw, double turnPitch) {
		active = true;
		
		turnYawAmount = turnYaw;
		turnPitchAmount = turnPitch;
	}
	
	public void update() {
		if(active) {
			active = false;
			
			apply();
			
			return;
		}
		
		reset();
	}
	
	private void apply() {
		LocalPlayer p = PlayerContext.player();
		
		float f = TURN_SPEED * GameContext.deltaTime();
		
		p.turn(turnYawAmount * f, turnPitchAmount * f);
	}
	
	private void reset() {
		turnYawAmount = 0;
		turnPitchAmount = 0;
	}
	
}
