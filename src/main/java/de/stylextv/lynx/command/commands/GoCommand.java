package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.pathing.calc.PathFinder;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.state.StateManager;
import de.stylextv.lynx.util.ChatUtil;

public class GoCommand extends Command {
	
	public GoCommand() {
		super("go", "Starts moving to the set goal.");
	}
	
	@Override
	public void execute(String[] args) {
		Goal goal = StateManager.getGoal();
		
		if(goal == null) {
			ChatUtil.sendToUser("§cNo goal present!");
			
			return;
		}
		
		ChatUtil.sendToUser("Started. §8(Goal: " + goal + ")");
		
		PathFinder finder = new PathFinder(goal);
		
		Path path = finder.find(PlayerContext.blockPosition());
		
		MovementExecutor.followPath(path);
	}
	
}
