package de.stylextv.lynx.context;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class PlayerContext {
	
	private static final Minecraft MC = Minecraft.getInstance();
	
	public static ClientPlayerEntity player() {
		return MC.player;
	}
	
	public static Vector3d position() {
		return player().position();
	}
	
	public static BlockPos blockPosition() {
		return player().blockPosition();
	}
	
	public static BlockPos feetPosition() {
		return new BlockPos(position().add(0, 0.125f, 0));
	}
	
	public static double horizontalSpeed() {
		Vector3d v = MC.player.getDeltaMovement();
		
		return v.multiply(1, 0, 1).length();
	}
	
	public static double verticalSpeed() {
		Vector3d v = MC.player.getDeltaMovement();
		
		return v.y();
	}
	
	public static double speed() {
		return MC.player.getDeltaMovement().length();
	}
	
	public static boolean isOnGround() {
		return MC.player.isOnGround();
	}
	
	public static double distanceSqr(BlockPos pos) {
		float x = pos.getX() + 0.5f;
		float y = pos.getY() + 0.5f;
		float z = pos.getZ() + 0.5f;
		
		return distanceSqr(x, y, z);
	}
	
	public static double distanceSqr(double x, double y, double z) {
		Vector3d pos = position();
		
		return pos.distanceToSqr(x, y, z);
	}
	
	public static PlayerController controller() {
		return MC.gameMode;
	}
	
	public static RayTraceResult objectUnderCrosshair() {
		return MC.hitResult;
	}
	
	public static void closeContainer() {
		player().closeContainer();
	}
	
	public static boolean setFlying(boolean b) {
		return MC.player.abilities.flying = b;
	}
	
}
