package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;

public class AwarenessController {
	
	public static BlockHitResult getBlockTarget() {
		HitResult result = getCrosshairTarget();
		
		if(result == null || result.getType() != Type.BLOCK) return null;
		
		return (BlockHitResult) result;
	}
	
	public static HitResult getCrosshairTarget() {
		return PlayerContext.crosshairTarget();
	}
	
}
