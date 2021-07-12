package de.stylextv.lynx.pathing.movement;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.Input;
import de.stylextv.lynx.input.controller.BuildingController;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.movements.AscendMovement;
import de.stylextv.lynx.pathing.movement.movements.BreakBlockMovement;
import de.stylextv.lynx.pathing.movement.movements.DescendMovement;
import de.stylextv.lynx.pathing.movement.movements.DiagonalMovement;
import de.stylextv.lynx.pathing.movement.movements.FallMovement;
import de.stylextv.lynx.pathing.movement.movements.StraightMovement;
import de.stylextv.lynx.util.ChatUtil;
import net.minecraft.util.math.BlockPos;

public class MovementExecutor {
	
	private static Path path;
	
	private static Movement movement;
	
	private static Node previousNode;
	
	public static void gotoGoal() {
		Goal goal = MemoryManager.getGoal();
		
		PathFinder finder = new PathFinder(goal);
		
		Path path = finder.find(PlayerContext.feetPosition());
		
		followPath(path);
	}
	
	public static void followPath(Path p) {
		if(p == null) {
			ChatUtil.send("§cPath not found!");
			
			return;
		}
		
		path = p;
		
		nextNode();
	}
	
	public static void stop() {
		path = null;
		movement = null;
		
		clearInputs();
	}
	
	private static void clearInputs() {
		InputController.releaseAll();
	}
	
	public static void onRenderTick() {
		if(path == null) return;
		
		if(movement == null) {
			
			Node n = path.getCurrentNode();
			
			newMovement(n);
			
		} else {
			
			movement.onRenderTick();
			
			if(PlayerContext.isInWater()) InputController.setPressed(Input.JUMP, true);
			
			MovementState state = movement.getState();
			
			if(state != MovementState.GOING) {
				movement = null;
				
				if(state == MovementState.REACHED_NODE) nextNode();
			}
		}
	}
	
	private static void nextNode() {
		previousNode = path.getCurrentNode();
		
		path.next();
		
		if(path.isFinished()) {
			ChatUtil.send("Destination reached.");
			
			stop();
		}
	}
	
	private static void newMovement(Node n) {
		clearInputs();
		
		BlockPos pos = blockToBreak(n);
		
		if(pos != null) {
			
			movement = new BreakBlockMovement(n, pos);
			
			return;
		}
		
		int x = previousNode.getX();
		int y = previousNode.getY();
		int z = previousNode.getZ();
		
		Movement m = null;
		
		if(y != n.getY()) {
			
			boolean ascend = n.getY() > y;
			
			if(ascend) {
				m = new AscendMovement(n);
			} else {
				
				int fallDistance = y - n.getY();
				
				if(fallDistance == 1) m = new DescendMovement(n);
				else m = new FallMovement(n);
			}
			
		} else {
			
			int disX = Math.abs(x - n.getX());
			int disZ = Math.abs(z - n.getZ());
			
			int dis = disX + disZ;
			
			boolean diagonally = dis > 1;
			
			if(diagonally) m = new DiagonalMovement(n);
			else m = new StraightMovement(n);
		}
		
		movement = m;
	}
	
	private static BlockPos blockToBreak(Node n) {
		List<BlockPos> list = blocksToBreak(n);
		
		if(list.isEmpty()) return null;
		
		BlockPos pos = null;
		
		double dis = 0;
		
		for(BlockPos p : list) {
			if(!ViewController.canSee(p)) continue;
			
			double d = ViewController.getViewDistance(p);
			
			if(pos == null || d < dis) {
				
				pos = p;
				dis = d;
			}
		}
		
		return pos;
	}
	
	private static List<BlockPos> blocksToBreak(Node n) {
		ArrayList<BlockPos> list = new ArrayList<>();
		
		addBlockToBreak(list, n.getX(), n.getY(), n.getZ());
		addBlockToBreak(list, n.getX(), n.getY() + 1, n.getZ());
		
		int y = previousNode.getY();
		
		if(n.getY() != y) {
			
			Node higherNode = n;
			Node lowerNode = previousNode;
			
			if(higherNode.getY() < lowerNode.getY()) {
				
				higherNode = previousNode;
				lowerNode = n;
			}
			
			int bonkX = lowerNode.getX();
			int bonkY = higherNode.getY() + 1;
			int bonkZ = lowerNode.getZ();
			
			addBlockToBreak(list, bonkX, bonkY, bonkZ);
		}
		
		return list;
	}
	
	private static void addBlockToBreak(ArrayList<BlockPos> list, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		
		if(BuildingController.canBreakBlock(pos)) {
			list.add(pos);
		}
	}
	
	public static Path getPath() {
		return path;
	}
	
}
