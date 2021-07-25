package de.stylextv.lynx.input.controller;

import java.util.ArrayList;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public class AwarenessController {
	
	private static final float PLAYER_REACH = 4f; // 4.5f
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
		LocalPlayer p = PlayerContext.player();
		
		double dx = p.getX() - x;
		double dy = p.getEyeY() - y;
		double dz = p.getZ() - z;
		
		return dx * dx + dy * dy + dz * dz < PLAYER_REACH_SQR;
	}
	
	public static BlockHitResult getBlockUnderCrosshair() {
		HitResult result = getObjectUnderCrosshair();
		
		if(result == null || result.getType() != Type.BLOCK) return null;
		
		BlockHitResult blockResult = (BlockHitResult) result;
		
		return blockResult;
	}
	
	public static HitResult getObjectUnderCrosshair() {
		return PlayerContext.objectUnderCrosshair();
	}
	
}
