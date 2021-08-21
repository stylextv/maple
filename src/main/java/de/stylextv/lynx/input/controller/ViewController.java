package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.input.SmoothLook;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.util.world.Rotation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;

public class ViewController {
	
	private static SmoothLook smoothLook = new SmoothLook();
	
	public static void lookAt(Entity e) {
		Vec3 pos = e.position();
		
		lookAt(pos.x(), pos.y() + e.getEyeHeight(), pos.z());
	}
	
	public static void lookAt(BlockPos pos) {
		lookAt(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
	}
	
	public static void lookAt(Node n) {
		lookAt(n, false);
	}
	
	public static void lookAt(Node n, boolean lookDown) {
		double y = PlayerContext.eyePosition().y();
		
		lookAt(n.getX() + 0.5, lookDown ? n.getY() + 0.5 : y, n.getZ() + 0.5);
	}
	
	public static void lookAt(double x, double y, double z) {
		Rotation r = getTargetRotation(x, y, z);
		
		smoothLook.setRotation(r);
	}
	
	public static Rotation getTargetRotation(double x, double y, double z) {
		Vec3 v = PlayerContext.eyePosition();
		
		double dx = v.x() - x;
		double dy = v.y() - y;
		double dz = v.z() - z;
		
		float yaw = (float) Math.toDegrees(Math.atan2(dz, dx) + Math.PI / 2) % 360;
		float pitch = (float) Math.toDegrees(-Math.atan2(Math.sqrt(dz * dz + dx * dx), dy) + Math.PI / 2);
		
		return new Rotation(yaw, pitch);
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
