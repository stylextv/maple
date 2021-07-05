package de.stylextv.dwarf.task;

import de.stylextv.dwarf.input.PlayerContext;
import de.stylextv.dwarf.input.controller.MovementController;
import de.stylextv.dwarf.pathing.Path;
import de.stylextv.dwarf.pathing.PathFinder;
import de.stylextv.dwarf.pathing.goal.IGoal;

public class MoveTask extends Task {
	
	private IGoal goal;
	
	public MoveTask(IGoal goal) {
		this.goal = goal;
	}
	
	@Override
	public void start() {
		PathFinder finder = new PathFinder(goal);
		
		Path path = finder.find(PlayerContext.blockPosition());
		
		MovementController.followPath(path);
	}
	
	@Override
	public void stop() {
		MovementController.stop();
	}
	
	@Override
	public boolean run() {
		return !MovementController.isMoving();
	}
	
}
