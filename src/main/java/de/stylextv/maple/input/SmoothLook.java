package de.stylextv.maple.input;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.util.world.Rotation;
import net.minecraft.client.network.ClientPlayerEntity;

public class SmoothLook {
	
	private boolean active;
	
	private Rotation rotation;
	
	public void setRotation(Rotation r) {
		active = true;
		
		rotation = r;
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
		ClientPlayerEntity p = PlayerContext.player();
		
		float yaw = p.getYaw();
		float pitch = p.getPitch();
		
		Rotation r = new Rotation(yaw, pitch);
		
		Rotation target = rotation.calibrateYaw(r);
		
		p.setYaw(target.getYaw());
		p.setPitch(target.getPitch());
	}
	
	private void reset() {
		rotation = null;
	}
	
}
