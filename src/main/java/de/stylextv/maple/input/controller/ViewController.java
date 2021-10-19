package de.stylextv.maple.input.controller;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.input.SmoothLook;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.util.world.Offset;
import de.stylextv.maple.util.world.rotation.Rotation;
import de.stylextv.maple.util.world.rotation.RotationUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class ViewController {
	
	private static SmoothLook smoothLook = new SmoothLook();
	
	public static void lookAt(Entity e) {
		Vec3d pos = e.getPos();
		
		EntityPose pose = e.getPose();
		
		float eyeHeight = e.getEyeHeight(pose);
		
		double x = pos.getX();
		double y = pos.getY() + eyeHeight;
		double z = pos.getZ();
		
		lookAt(x, y, z);
	}
	
	public static void lookAt(BlockPos pos) {
		double x = pos.getX() + 0.5;
		double y = pos.getY() + 0.5;
		double z = pos.getZ() + 0.5;
		
		lookAt(x, y, z);
	}
	
	public static void lookAt(Offset o) {
		lookAt(o.getX(), o.getY(), o.getZ());
	}
	
	public static void lookAt(Node n) {
		lookAt(n, false);
	}
	
	public static void lookAt(Node n, boolean lookDown) {
		double x = n.getX() + 0.5;
		double y = n.getY() + 0.5;
		double z = n.getZ() + 0.5;
		
		if(lookDown) lookAt(x, y, z);
		else lookAt(x, z);
	}
	
	public static void lookAt(double x, double z) {
		Vec3d v = PlayerContext.eyePosition();
		
		double y = v.getY();
		
		lookAt(x, y, z);
	}
	
	public static void lookAt(double x, double y, double z) {
		Vec3d v = PlayerContext.eyePosition();
		
		double dx = v.getX() - x;
		double dy = v.getY() - y;
		double dz = v.getZ() - z;
		
		Rotation r = RotationUtil.vecToRotation(dx, dy, dz);
		
		smoothLook.setRotation(r);
	}
	
	public static boolean canSee(BlockPos pos) {
		Vec3d v = Vec3d.ofCenter(pos);
		
		return canSee(v, pos);
	}
	
	public static boolean canSee(Offset o) {
		return canSee(o.getX(), o.getY(), o.getZ());
	}
	
	public static boolean canSee(Offset o, BlockPos ignorePos) {
		return canSee(o.getX(), o.getY(), o.getZ(), ignorePos);
	}
	
	public static boolean canSee(double x, double y, double z) {
		return canSee(x, y, z, null);
	}
	
	public static boolean canSee(double x, double y, double z, BlockPos ignorePos) {
		Vec3d v = new Vec3d(x, y, z);
		
		return canSee(v, ignorePos);
	}
	
	public static boolean canSee(Vec3d v, BlockPos ignorePos) {
		BlockHitResult result = raycastFromPlayer(v);
		
		Type type = result.getType();
		
		if(type == Type.MISS) return true;
		
		if(type != Type.BLOCK || ignorePos == null) return false;
		
		BlockPos pos = result.getBlockPos();
		
		return pos.equals(ignorePos);
	}
	
	private static BlockHitResult raycastFromPlayer(Vec3d end) {
		ClientPlayerEntity p = PlayerContext.player();
		
		Vec3d eyePos = p.getEyePos();
		
		return raycast(eyePos, end, p);
	}
	
	private static BlockHitResult raycast(Vec3d start, Vec3d end, Entity e) {
		ClientWorld world = WorldContext.world();
		
		RaycastContext context = new RaycastContext(start, end, ShapeType.COLLIDER, FluidHandling.NONE, e);
		
		return world.raycast(context);
	}
	
	public static void onRenderTick() {
		smoothLook.update();
	}
	
}
