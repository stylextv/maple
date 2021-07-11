package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.memory.MemoryManager;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.movements.AscendMovement;
import de.stylextv.lynx.pathing.movement.movements.DescendMovement;
import de.stylextv.lynx.pathing.movement.movements.DiagonalMovement;
import de.stylextv.lynx.pathing.movement.movements.FallMovement;
import de.stylextv.lynx.pathing.movement.movements.StraightMovement;
import de.stylextv.lynx.util.ChatUtil;

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
			
			if(movement.isCompleted()) {
				movement = null;
				
				nextNode();
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
		
		clearInputs();
		
		movement = m;
	}
	
	public static Path getPath() {
		return path;
	}
	
}
