package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.input.SmoothLook;
import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class ViewController {
	
	private static SmoothLook smoothLook = new SmoothLook();
	
	public static void lookAt(Entity e) {
		Vec3 pos = e.position();
		
		lookAt(pos.x(), pos.y() + e.getBbHeight() / 2f, pos.z());
	}
	
	public static void lookAt(BlockPos pos) {
		lookAt(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
	}
	
	public static void lookAt(Node n) {
		lookAt(n, false);
	}
	
	public static void lookAt(Node n, boolean lookDown) {
		double y = PlayerContext.player().getEyeY();
		
		lookAt(n.getX() + 0.5, lookDown ? n.getY() + 0.5 : y, n.getZ() + 0.5);
	}
	
	public static void lookAt(double x, double y, double z) {
		Vec2 v = getViewVector(x, y, z);
		
		smoothLook.feedInput(v.x, v.y);
	}
	
	public static double getViewDistance(BlockPos pos) {
		return getViewDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
	}
	
	public static double getViewDistance(double x, double y, double z) {
		Vec2 v = getViewVector(x, y, z);
		
		return Math.sqrt(v.x * v.x + v.y * v.y);
	}
	
	public static Vec2 getViewVector(double x, double y, double z) {
		LocalPlayer p = PlayerContext.player();
		
		Vec2 v = p.getRotationVector();
		
		float yaw = v.y % 360;
		float pitch = v.x;
		
		double dx = p.getX() - x;
		double dy = p.getEyeY() - y;
		double dz = p.getZ() - z;
		
		float targetYaw = (float) Math.toDegrees(Math.atan2(dz, dx) + Math.PI / 2) % 360;
		float targetPitch = (float) Math.toDegrees(-Math.atan2(Math.sqrt(dz * dz + dx * dx), dy) + Math.PI / 2);
		
		if(yaw < 0) yaw += 360;
		if(targetYaw < 0) targetYaw += 360;
		
		float yawDis1 = targetYaw - yaw;
		float yawDis2 = yaw < targetYaw ? -yaw - 360 + targetYaw : targetYaw + 360 - yaw;
		
		float yawDis = Math.abs(yawDis1) < Math.abs(yawDis2) ? yawDis1 : yawDis2;
		float pitchDis = targetPitch - pitch;
		
		return new Vec2(yawDis, pitchDis);
	}
	
	public static boolean canSee(BlockPos pos) {
		LocalPlayer p = PlayerContext.player();
		
		ClientLevel level = LevelContext.level();
		
		Vec3 v1 = new Vec3(p.getX(), p.getEyeY(), p.getZ());
		Vec3 v2 = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
		
		BlockHitResult result = level.clip(new ClipContext(v1, v2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p));
		
		if(result.getType() == BlockHitResult.Type.MISS) return true;
		if(result.getType() == BlockHitResult.Type.BLOCK && result.getBlockPos().equals(pos)) return true;
		
		return false;
	}
	
	public static boolean canSee(double x, double y, double z) {
		LocalPlayer p = PlayerContext.player();
		
		ClientLevel level = LevelContext.level();
		
		Vec3 v1 = new Vec3(p.getX(), p.getEyeY(), p.getZ());
		Vec3 v2 = new Vec3(x, y, z);
		
		return level.clip(new ClipContext(v1, v2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p)).getType() == Type.MISS;
	}
	
	public static void onRenderTick() {
		smoothLook.update();
	}
	
}
