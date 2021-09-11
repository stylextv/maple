package de.stylextv.lynx.input;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.util.world.Rotation;
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
		
//		Vec2 v = p.getRotationVector();
//		
//		float yaw = v.y;
//		float pitch = v.x;
//		
//		Rotation r = new Rotation(yaw, pitch);
//		
//		r = r.normalizeYaw().nudgeToMatch(rotation, TURN_SPEED);
		
		p.setYaw(rotation.getYaw());
		p.setPitch(rotation.getPitch());
	}
	
	private void reset() {
		rotation = null;
	}
	
}
