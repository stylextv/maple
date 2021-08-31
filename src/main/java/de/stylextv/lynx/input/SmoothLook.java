package de.stylextv.lynx.input;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.util.world.Rotation;
import net.minecraft.client.player.LocalPlayer;

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
		LocalPlayer p = PlayerContext.player();
		
//		Vec2 v = p.getRotationVector();
//		
//		float yaw = v.y;
//		float pitch = v.x;
//		
//		Rotation r = new Rotation(yaw, pitch);
//		
//		r = r.normalizeYaw().nudgeToMatch(rotation, TURN_SPEED);
		
		p.setXRot(rotation.getPitch());
		p.setYRot(rotation.getYaw());
	}
	
	private void reset() {
		rotation = null;
	}
	
}
