package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.WorldContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

public class PlaceController {
	
	private static BlockPos target;
	
	private static BlockPos support;
	
	public static void placeBlock(BlockPos pos, BlockPos supportPos) {
		target = pos;
		support = supportPos;
	}
	
	public static void stop() {
		target = null;
	}
	
	public static void onTick() {
		if(!hasTarget() || !WorldContext.isIngame()) return;
		
		BlockState state = WorldContext.getBlockState(target);
		
		if(!canBreakBlock(target)) {
			
			stop();
			
			return;
		}
		
		Direction dir = getTargetFace();
		
		if(dir != null) {
			PlayerController controller = PlayerContext.controller();
			
			controller.continueDestroyBlock(target, dir);
			
			PlayerContext.player().swing(Hand.MAIN_HAND);
		}
	}
	
	public static void onRenderTick() {
		if(!hasTarget() || !WorldContext.isIngame()) return;
		
		ViewController.lookAt(target);
	}
	
	private static Direction getTargetFace() {
		BlockRayTraceResult blockResult = AwarenessController.getBlockUnderCrosshair();
		
		if(blockResult == null) return null;
		
		BlockPos pos = blockResult.getBlockPos();
		
		if(!pos.equals(target)) return null;
		
		return blockResult.getDirection();
	}
	
	public static boolean hasTarget() {
		return target != null;
	}
	
}
