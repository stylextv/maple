package de.stylextv.lynx.util;

import de.stylextv.lynx.util.world.Rotation;

public class RotationUtil {
	
    public static final double DEG_TO_RAD = Math.PI / 180;
    
    public static final double RAD_TO_DEG = 180 / Math.PI;
    
	private static final double PI_OVER_TWO = Math.PI / 2;
	
	public static Rotation vecToRotation(double x, double y, double z) {
		double l = Math.sqrt(z * z + x * x);
		
		double tan1 = Math.atan2(z, x);
		double tan2 = Math.atan2(l, y);
		
		float yaw = (float) ((tan1 + PI_OVER_TWO) * RAD_TO_DEG);
		float pitch = (float) ((-tan2 + PI_OVER_TWO) * RAD_TO_DEG);
		
		Rotation r = new Rotation(yaw, pitch);
		
		return r.normalizeYaw();
	}
	
}
