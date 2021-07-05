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
	
	private static final float NEXT_NODE_HDIS = 1f;
	private static final float NEXT_NODE_VDIS = 1.5f;
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
		
		ClientPlayerEntity p = PlayerContext.player();
		
		Node n = path.getCurrentNode();
		
		boolean move = horizontalDistance(n) > MIN_MOVING_DIS;
		
		InputController.setPressed(Input.MOVE_FORWARD, move);
		InputController.setPressed(Input.SPRINT, true);
		
		InputController.setPressed(Input.JUMP, shouldJump(p));
		
		ViewController.lookAt(n);
		
		if(hasReachedNode(n)) {
			path.next();
			
			if(path.isFinished()) stop();
		}
	}
	
	private static boolean shouldJump(ClientPlayerEntity p) {
		return shouldJump(p, 0, 10f) || shouldJump(p, -45, 5f) || shouldJump(p, 45, 5f);
	}
	
	private static boolean shouldJump(ClientPlayerEntity p, float angle, float dis) {
		double rot = Math.toRadians(90f - p.yRot + angle);
		
		double m = (0.03f + PlayerContext.horizontalSpeed()) * dis;
		
		double x = -Math.cos(rot) * m;
		double z = Math.sin(rot) * m;
		
		Vector3d v = p.position().add(x, 0, z);
		
		BlockPos pos = new BlockPos(v);
		
		return WorldCache.getBlockType(pos) != BlockType.AIR;
	}
	
	private static boolean hasReachedNode(Node n) {
		if(verticalDistance(n) > NEXT_NODE_VDIS) return false;
		
		return horizontalDistance(n) <= NEXT_NODE_HDIS;
	}
	
	private static double horizontalDistance(Node n) {
		Vector3d pos = PlayerContext.position();
		
		Vector3d v = pos.subtract(n.getX() + 0.5f, pos.y(), n.getZ() + 0.5f);
		
		return v.length();
	}
	
	private static double verticalDistance(Node n) {
		Vector3d pos = PlayerContext.position();
		
		return pos.y() + NEXT_NODE_VDIS_OFFSET - n.getY();
	}
	
	public static boolean isMoving() {
		return path != null;
	}
	
	public static Path getPath() {
		return path;
	}
	
}
