package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.calc.goal.StrictDirectionGoal;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.util.chat.ChatUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class TunnelCommand extends Command {
	
	public TunnelCommand() {
		super("tunnel", "Tunnel in the direction you are looking.");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		BlockPos pos = PlayerContext.feetPosition();
		
		Direction dir = PlayerContext.horizontalDirection();
		
		Goal goal = new StrictDirectionGoal(pos, dir);
		
		TaskManager.gotoGoal(goal);
		
		ChatUtil.send("Started tunneling.");
		
		return true;
	}
	
}
