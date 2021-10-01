package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.util.world.Offset;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Vec3d;

public class AwarenessController {
	
	// TODO rename
	private static final Offset[] UPPER_PLAYER_HITBOX_CORNERS = {
			new Offset(-0.3,  2.9, -0.3),
			new Offset( 0.3,  2.9, -0.3),
			new Offset(-0.3,  2.9,  0.3),
			new Offset( 0.3,  2.9,  0.3)
	};
	
	public static boolean canJump() {
		if(!PlayerContext.isOnGround()) return false;
		
		Vec3d v = PlayerContext.position();
		
		for(Offset o : UPPER_PLAYER_HITBOX_CORNERS) {
			
			double x = v.getX() + o.getX();
			double y = v.getY() + o.getY();
			double z = v.getZ() + o.getZ();
			
			BlockType type = CacheManager.getBlockType(x, y, z);
			
			if(!type.isPassable()) return false;
		}
		
		return true;
	}
	
	public static BlockHitResult getBlockTarget() {
		HitResult result = getCrosshairTarget();
		
		if(result == null || result.getType() != Type.BLOCK) return null;
		
		return (BlockHitResult) result;
	}
	
	public static HitResult getCrosshairTarget() {
		return PlayerContext.crosshairTarget();
	}
	
}
