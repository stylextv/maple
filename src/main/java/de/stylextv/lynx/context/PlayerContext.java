package de.stylextv.lynx.context;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PlayerContext {
	
	private static final Minecraft MC = Minecraft.getInstance();
	
	public static LocalPlayer player() {
		return MC.player;
	}
	
	public static Inventory inventory() {
		return player().getInventory();
	}
	
	public static Vec3 position() {
		float time = GameContext.frameTime();
		
		return player().getPosition(time);
	}
	
	public static BlockPos blockPosition() {
		return player().blockPosition();
	}
	
	public static Vec3 eyePosition() {
		float time = GameContext.frameTime();
		
		return player().getEyePosition(time);
	}
	
	public static BlockPos feetPosition() {
		return new BlockPos(position().add(0, 0.125f, 0));
	}
	
	public static ChunkPos chunkPosition() {
		return player().chunkPosition();
	}
	
	public static double horizontalSpeed() {
		Vec3 v = MC.player.getDeltaMovement();
		
		return v.multiply(1, 0, 1).length();
	}
	
	public static double verticalSpeed() {
		Vec3 v = MC.player.getDeltaMovement();
		
		return v.y();
	}
	
	public static double speed() {
		return MC.player.getDeltaMovement().length();
	}
	
	public static boolean isOnGround() {
		return MC.player.isOnGround();
	}
	
	public static boolean isInWater() {
		return MC.player.isInWater();
	}
	
	public static double distanceSqr(BlockPos pos) {
		float x = pos.getX() + 0.5f;
		float y = pos.getY() + 0.5f;
		float z = pos.getZ() + 0.5f;
		
		return distanceSqr(x, y, z);
	}
	
	public static double distanceSqr(double x, double y, double z) {
		Vec3 pos = position();
		
		return pos.distanceToSqr(x, y, z);
	}
	
	public static MultiPlayerGameMode gameMode() {
		return MC.gameMode;
	}
	
	public static HitResult objectUnderCrosshair() {
		return MC.hitResult;
	}
	
	public static void closeContainer() {
		player().closeContainer();
	}
	
	public static boolean setFlying(boolean b) {
		return MC.player.getAbilities().flying = b;
	}
	
}
