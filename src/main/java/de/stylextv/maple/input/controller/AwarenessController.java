package de.stylextv.maple.input.controller;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.util.world.Offset;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Vec3d;

public class AwarenessController {
	
	private static final Offset[] BONK_HEAD_CORNERS = {
			new Offset(-0.3,  2.9, -0.3),
			new Offset( 0.3,  2.9, -0.3),
			new Offset(-0.3,  2.9,  0.3),
			new Offset( 0.3,  2.9,  0.3)
	};
	
	public static boolean canJump() {
		if(!PlayerContext.isOnGround()) return false;
		
		Vec3d v = PlayerContext.position();
		
		for(Offset o : BONK_HEAD_CORNERS) {
			
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
