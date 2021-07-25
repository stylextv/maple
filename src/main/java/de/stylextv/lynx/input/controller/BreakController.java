package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.context.LevelContext;

public class BreakController {
	
	private static BlockPos target;
	
	public static void breakBlock(BlockPos pos) {
		if(!canBreakBlock(pos)) return;
		
		target = pos;
	}
	
	public static boolean canBreakBlock(BlockPos pos) {
		BlockState state = LevelContext.getBlockState(pos);
		
		return state.getMaterial().blocksMotion();
	}
	
	public static void stop() {
		target = null;
	}
	
	public static void onTick() {
		if(!hasTarget() || !GameContext.isIngame()) return;
		
		if(!canBreakBlock(target)) {
			
			stop();
			
			return;
		}
		
		Direction dir = getTargetFace();
		
		if(dir != null) {
			MultiPlayerGameMode gameMode = PlayerContext.gameMode();
			
			gameMode.continueDestroyBlock(target, dir);
			
			PlayerContext.player().swing(InteractionHand.MAIN_HAND);
		}
	}
	
	public static void onRenderTick() {
		if(!hasTarget() || !GameContext.isIngame()) return;
		
		ViewController.lookAt(target);
	}
	
	private static Direction getTargetFace() {
		BlockHitResult result = AwarenessController.getBlockUnderCrosshair();
		
		if(result == null) return null;
		
		BlockPos pos = result.getBlockPos();
		
		if(!pos.equals(target)) return null;
		
		return result.getDirection();
	}
	
	public static boolean hasTarget() {
		return target != null;
	}
	
}
