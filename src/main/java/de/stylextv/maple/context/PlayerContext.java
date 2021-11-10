package de.stylextv.maple.context;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class PlayerContext {
	
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	
	public static ClientPlayerEntity player() {
		return CLIENT.player;
	}
	
	public static PlayerInventory inventory() {
		return player().getInventory();
	}
	
	public static Vec3d position() {
		float tickDelta = GameContext.tickDelta();
		
		return player().getLerpedPos(tickDelta);
	}
	
	public static BlockPos blockPosition() {
		return player().getBlockPos();
	}
	
	public static Direction horizontalDirection() {
		return player().getHorizontalFacing();
	}
	
	public static Vec3d eyePosition() {
		Vec3d v = position();
		
		ClientPlayerEntity p = player();
		
		EntityPose pose = p.getPose();
		
		float eyeHeight = p.getEyeHeight(pose);
		
		return v.add(0, eyeHeight, 0);
	}
	
	public static BlockPos feetPosition() {
		return new BlockPos(position().add(0, 0.4f, 0));
	}
	
	public static ChunkPos chunkPosition() {
		return player().getChunkPos();
	}
	
	public static double horizontalSpeed() {
		Vec3d v = velocity();
		
		return v.horizontalLength();
	}
	
	public static double verticalSpeed() {
		return velocity().getY();
	}
	
	public static double speed() {
		return velocity().length();
	}
	
	public static Vec3d velocity() {
		return player().getVelocity();
	}
	
	public static boolean isFalling() {
		if(isOnGround()) return false;
		
		return velocity().getY() < 0;
	}
	
	public static boolean isOnGround() {
		return player().isOnGround();
	}
	
	public static boolean isInWater() {
		return player().isTouchingWater();
	}
	
	public static double squaredDistanceTo(BlockPos pos) {
		float x = pos.getX() + 0.5f;
		float y = pos.getY();
		float z = pos.getZ() + 0.5f;
		
		return squaredDistanceTo(x, y, z);
	}
	
	public static double squaredDistanceTo(double x, double z) {
		Vec3d pos = position();
		
		double y = pos.getY();
		
		return squaredDistanceTo(x, y, z);
	}
	
	public static double squaredDistanceTo(double x, double y, double z) {
		Vec3d pos = position();
		
		return pos.squaredDistanceTo(x, y, z);
	}
	
	public static float reachDistance() {
		ClientPlayerInteractionManager manager = GameContext.interactionManager();
		
		return manager.getReachDistance();
	}
	
	public static HitResult crosshairTarget() {
		return CLIENT.crosshairTarget;
	}
	
	public static boolean setFlying(boolean b) {
		return player().getAbilities().flying = b;
	}
	
}
