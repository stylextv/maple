package de.stylextv.maple.input.controller;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.util.world.CollisionUtil;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class AwarenessController {
	
	private static final Box HEAD_COLLISION_BOX = new Box(-0.3, 0.9, -0.3, 0.3, 2.9, 0.3);
	
	public static boolean isSafeToBreak(BlockPos pos) {
		BlockPos from = PlayerContext.feetPosition();
		
		return isSafeToBreak(pos, from, false, true);
	}
	
	public static boolean isSafeToBreak(BlockPos pos, BlockPos from, boolean digStraightDown, boolean propagate) {
		if(!BreakController.isSafeToBreak(pos)) return false;
		
		BlockPos down = from.down();
		
		if(!digStraightDown && down.equals(pos)) return false;
		
		pos = pos.up();
		
		boolean falls = isFallingBlock(pos);
		
		if(falls) {
			
			int y = from.getY();
			
			if(y < pos.getY()) {
				
				int x = from.getX();
				int z = from.getZ();
				
				boolean b = x == pos.getX() && z == pos.getZ();
				
				if(b) return false;
			}
			
			if(!propagate) return true;
			
			return isSafeToBreak(pos, from, digStraightDown, propagate);
		}
		
		return true;
	}
	
	public static boolean awaitsFallingBlock(BlockPos pos) {
		BlockPos up = pos.up();
		
		if(isFallingBlock(up)) return true;
		
		Box box = new Box(pos, up);
		
		box = box.stretch(1, 1, 1);
		
		return CollisionUtil.collidesWithEntities(box, EntityType.FALLING_BLOCK);
	}
	
	public static boolean isFallingBlock(BlockPos pos) {
		BlockState state = BlockInterface.getState(pos);
		
		Block block = state.getBlock();
		
		return block instanceof FallingBlock;
	}
	
	public static boolean canJump() {
		if(!PlayerContext.isOnGround()) return false;
		
		Vec3d v = PlayerContext.position();
		
		Box box = HEAD_COLLISION_BOX.offset(v);
		
		return !CollisionUtil.collidesWithBlocks(box);
	}
	
	public static boolean canReach(BlockPos pos) {
		Vec3d eyePos = PlayerContext.eyePosition();
		
		Vec3d v = Vec3d.ofCenter(pos);
		
		double dis = eyePos.distanceTo(v);
		
		return dis <= PlayerContext.reachDistance();
	}
	
	public static boolean isBlockingPos(BlockPos pos) {
		ClientPlayerEntity p = PlayerContext.player();
		
		Box box = p.getBoundingBox();
		
		Box other = new Box(pos);
		
		return box.intersects(other);
	}
	
	public static BlockPos getTargetedPos() {
		BlockHitResult result = getBlockTarget();
		
		if(result == null) return null;
		
		return result.getBlockPos();
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
