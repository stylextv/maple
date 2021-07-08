package de.stylextv.lynx.input.controller;

import java.util.ArrayList;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.WorldContext;
import net.minecraft.block.Block;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;

public class AwarenessController {
	
	private static final float PLAYER_REACH = 4f;
	private static final float PLAYER_REACH_SQR = PLAYER_REACH * PLAYER_REACH;
	
	public static ArrayList<BlockPos> getBlocksInReach() {
		ArrayList<BlockPos> list = new ArrayList<>();
		
		BlockPos pos = PlayerContext.blockPosition();
		
		int l = (int) Math.ceil(PLAYER_REACH);
		
		int x = pos.getX();
		int y = pos.getY() + 1;
		int z = pos.getZ();
		
		for(int ox = -l; ox <= l; ox++) {
			for(int oy = -l; oy <= l; oy++) {
				for(int oz = -l; oz <= l; oz++) {
					BlockPos p = new BlockPos(x + ox, y + oy, z + oz);
					
					if(canReach(p)) list.add(p);
				}
			}
		}
		
		return list;
	}
	
	public static boolean canReach(BlockPos pos) {
		return canReach(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
	}
	
	public static boolean canReach(double x, double y, double z) {
		ClientPlayerEntity p = PlayerContext.player();
		
		double dx = p.getX() - x;
		double dy = p.getEyeY() - y;
		double dz = p.getZ() - z;
		
		return dx * dx + dy * dy + dz * dz < PLAYER_REACH_SQR;
	}
	
	public static BlockPos getBlockUnderCrosshair() {
		RayTraceResult result = getObjectUnderCrosshair();
		
		if(result == null || result.getType() != Type.BLOCK) return null;
		
		BlockRayTraceResult blockResult = (BlockRayTraceResult) result;
		
		return blockResult.getBlockPos();
	}
	
	public static RayTraceResult getObjectUnderCrosshair() {
		return PlayerContext.objectUnderCrosshair();
	}
	
	public static Block getBlock(BlockPos pos) {
		return WorldContext.world().getBlockState(pos).getBlock();
	}
	
}
