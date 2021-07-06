package de.stylextv.lynx.command;

import de.stylextv.lynx.input.PlayerContext;
import de.stylextv.lynx.input.controller.MovementController;
import de.stylextv.lynx.pathing.Path;
import de.stylextv.lynx.pathing.PathFinder;
import de.stylextv.lynx.pathing.goal.Goal;
import de.stylextv.lynx.state.StateManager;
import de.stylextv.lynx.util.ChatUtil;

public class StartCommand extends Command {
	
	public StartCommand() {
		super("start");
	}
	
	@Override
	public void execute(String[] args) {
		Goal goal = StateManager.getGoal();
		
		ChatUtil.sendToUser("Started. §8(Goal: " + goal + ")");
		
		PathFinder finder = new PathFinder(goal);
		
		Path path = finder.find(PlayerContext.blockPosition());
		
		System.out.println(path);
		
		MovementController.followPath(path);
	}
	
}
