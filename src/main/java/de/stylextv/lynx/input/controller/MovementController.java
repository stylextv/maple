package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.WorldCache;
import de.stylextv.lynx.input.Input;
import de.stylextv.lynx.input.PlayerContext;
import de.stylextv.lynx.pathing.Node;
import de.stylextv.lynx.pathing.Path;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class MovementController {
	
	private static final float NEXT_NODE_HDIS = 1.25f;
	private static final float NEXT_NODE_VDIS = 1.4f;
	private static final float NEXT_NODE_VDIS_OFFSET = -0.5f;
	
	private static final float MIN_MOVING_DIS = 0.5f;
	
	private static Path path;
	
	public static void followPath(Path p) {
		path = p;
	}
	
	public static void stop() {
		path = null;
		
		InputController.releaseAll();
	}
	
	public static void onTick() {
		if(path == null) return;
		
		Node n = path.getCurrentNode();
		
		boolean onGround = PlayerContext.isOnGround();
		
		boolean move = onGround || horizontalDistance(n) > MIN_MOVING_DIS;
		
		boolean jump = canJump() && shouldJump();
		
		InputController.setPressed(Input.MOVE_FORWARD, move);
		InputController.setPressed(Input.SPRINT, true);
		
		InputController.setPressed(Input.JUMP, jump);
		
		ViewController.lookAt(n);
		
		if(hasReachedNode(n)) {
			path.next();
			
			if(path.isFinished()) stop();
		}
	}
	
	private static boolean canJump() {
		BlockPos pos = PlayerContext.blockPosition().offset(0, 2, 0);
		
		return WorldCache.getBlockType(pos) == BlockType.AIR;
	}
	
	private static boolean shouldJump() {
		return shouldJump(0, 10f) || shouldJump(-45, 5f) || shouldJump(45, 5f);
	}
	
	private static boolean shouldJump(float angle, float dis) {
		ClientPlayerEntity p = PlayerContext.player();
		
		double rot = Math.toRadians(90f - p.yRot + angle);
		
		double m = (0.03f + PlayerContext.horizontalSpeed()) * dis;
		
		double x = -Math.cos(rot) * m;
		double z = Math.sin(rot) * m;
		
		Vector3d v = p.position().add(x, 0, z);
		
		BlockPos pos = new BlockPos(v);
		
		return WorldCache.getBlockType(pos) != BlockType.AIR;
	}
	
	private static boolean hasReachedNode(Node n) {
		BlockPos pos = PlayerContext.blockPosition();
		
		int y = pos.getY();
		
		if(!PlayerContext.isOnGround() && n.getY() > y) return false;
		
		return distanceToNode(n) <= NEXT_NODE_HDIS;
	}
	
	private static double distanceToNode(Node n) {
		Vector3d pos = PlayerContext.position();
		
		pos.add(0, NEXT_NODE_VDIS_OFFSET, 0);
		
		Vector3d v = pos.subtract(n.getX() + 0.5f, n.getY(), n.getZ() + 0.5f);
		
		v.multiply(1, NEXT_NODE_HDIS / NEXT_NODE_VDIS, 1);
		
		return v.length();
	}
	
	private static double horizontalDistance(Node n) {
		Vector3d pos = PlayerContext.position();
		
		Vector3d v = pos.subtract(n.getX() + 0.5f, pos.y(), n.getZ() + 0.5f);
		
		return v.length();
	}
	
	
	public static boolean isMoving() {
		return path != null;
	}
	
	public static Path getPath() {
		return path;
	}
	
}
