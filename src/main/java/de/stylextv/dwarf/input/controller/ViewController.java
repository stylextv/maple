package de.stylextv.dwarf.input.controller;

import de.stylextv.dwarf.input.PlayerContext;
import de.stylextv.dwarf.input.SmoothLook;
import de.stylextv.dwarf.pathing.Node;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

public class ViewController {
	
	private static SmoothLook smoothLook = new SmoothLook();
	
	public static void lookAt(Entity e) {
		Vector3d pos = e.position();
		
		lookAt(pos.x(), pos.y() + e.getBbHeight() / 2f, pos.z());
	}
	
	public static void lookAt(BlockPos pos) {
		lookAt(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
	}
	
	public static void lookAt(Node n) {
		double y = PlayerContext.player().getEyeY();
		
		lookAt(n.getX() + 0.5, y, n.getZ() + 0.5);
	}
	
	public static void lookAt(double x, double y, double z) {
		Vector2f v = getViewVector(x, y, z);
		
		smoothLook.feedInput(v.x, v.y);
	}
	
	public static double getViewDistance(BlockPos pos) {
		return getViewDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
	}
	
	public static double getViewDistance(double x, double y, double z) {
		Vector2f v = getViewVector(x, y, z);
		
		return Math.sqrt(v.x * v.x + v.y * v.y);
	}
	
	public static Vector2f getViewVector(double x, double y, double z) {
		ClientPlayerEntity p = PlayerContext.player();
		
		Vector2f v = p.getRotationVector();
		
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
		
		return new Vector2f(yawDis, pitchDis);
	}
	
	public static boolean canSee(BlockPos pos) {
		ClientPlayerEntity p = PlayerContext.player();
		
		Vector3d v1 = new Vector3d(p.getX(), p.getEyeY(), p.getZ());
		Vector3d v2 = new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
		
		BlockRayTraceResult result = PlayerContext.world().clip(new RayTraceContext(v1, v2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, p));
		
		if(result.getType() == RayTraceResult.Type.MISS) return true;
		if(result.getType() == RayTraceResult.Type.BLOCK && result.getBlockPos().equals(pos)) return true;
		
		return false;
	}
	
	public static boolean canSee(double x, double y, double z) {
		ClientPlayerEntity p = PlayerContext.player();
		
		Vector3d v1 = new Vector3d(p.getX(), p.getEyeY(), p.getZ());
		Vector3d v2 = new Vector3d(x, y, z);
		
		return PlayerContext.world().clip(new RayTraceContext(v1, v2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, p)).getType() == RayTraceResult.Type.MISS;
	}
	
	public static void onTick() {
		smoothLook.update();
	}
	
	public static void onRender() {
		smoothLook.apply();
	}
	
}
