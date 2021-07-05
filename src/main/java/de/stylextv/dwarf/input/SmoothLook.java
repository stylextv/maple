package de.stylextv.dwarf.input;

import net.minecraft.client.entity.player.ClientPlayerEntity;

public class SmoothLook {
	
	private static final float TURN_SPEED = 4f;
	
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
			
			return;
		}
		
		reset();
	}
	
	private void reset() {
		turnYawAmount = 0;
		turnPitchAmount = 0;
	}
	
	public void apply() {
		ClientPlayerEntity p = PlayerContext.player();
		
		float f = TURN_SPEED * PlayerContext.deltaTime();
		
		p.turn(turnYawAmount * f, turnPitchAmount * f);
	}
	
}
