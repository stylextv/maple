package de.stylextv.lynx.util.world;

public class Rotation {
	
	private float yaw;
	private float pitch;
	
	public Rotation(float yaw) {
		this(yaw, 0);
	}
	
	public Rotation(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Rotation add(Rotation r) {
		float yaw = r.getYaw();
		float pitch = r.getPitch();
		
		return add(yaw, pitch);
	}
	
	public Rotation add(float yaw, float pitch) {
		float newYaw = this.yaw + yaw;
		float newPitch = this.pitch + pitch;
		
		return new Rotation(newYaw, newPitch);
	}
	
	public Rotation subtract(Rotation r) {
		float yaw = r.getYaw();
		float pitch = r.getPitch();
		
		return subtract(yaw, pitch);
	}
	
	public Rotation subtract(float yaw, float pitch) {
		return add(-yaw, -pitch);
	}
	
	public Rotation nudgeToMatch(Rotation r) {
		return nudgeToMatch(r, 1000);
	}
	
	public Rotation nudgeToMatch(Rotation r, float amount) {
		Rotation delta = r.subtract(this);
		
		delta = delta.normalizeYaw();
		
		float dy = delta.getYaw();
		float dp = delta.getPitch();
		
		return nudge(dy, dp, amount);
	}
	
	public Rotation nudge(float dy, float dp, float amount) {
		float l = (float) Math.sqrt(dy * dy + dp * dp);
		
		float f1 = dy / l * amount;
		float f2 = dp / l * amount;
		
		if(Math.abs(f1) > Math.abs(dy)) f1 = dy;
		if(Math.abs(f2) > Math.abs(dp)) f2 = dp;
		
		return add(f1, f2);
	}
	
	public Rotation calibrateYaw(Rotation r) {
		float base = r.getYaw();
		
		r = r.normalizeYaw();
		
		base -= r.getYaw();
		
		Rotation target = r.nudgeToMatch(this);
		
		float newYaw = base + target.getYaw();
		
		return new Rotation(newYaw, pitch);
	}
	
	public Rotation normalizeYaw() {
		float newYaw = yaw % 360;
		
		if(newYaw < -180) {
			
			newYaw += 360;
			
		} else if(newYaw > 180) {
			
			newYaw -= 360;
		}
		
		return new Rotation(newYaw, pitch);
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
}
