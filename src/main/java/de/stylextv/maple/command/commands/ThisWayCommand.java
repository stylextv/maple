package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.calc.goal.XZGoal;
import de.stylextv.maple.task.TaskManager;
import de.stylextv.maple.util.chat.ChatUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ThisWayCommand extends Command {
	
	private static final int DEFAULT_DISTANCE = 1000;
	
	public ThisWayCommand() {
		super("thisway", "Move a specified number of blocks in the direction you are facing.", "forward");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		int dis = DEFAULT_DISTANCE;
		
		if(args.hasAtLeast(1)) {
			
			dis = args.getInt(0);
		}
		
		BlockPos pos = PlayerContext.feetPosition();
		
		ClientPlayerEntity p = PlayerContext.player();
		
		float yaw = p.getYaw();
		
		Goal goal = XZGoal.inDirection(pos, yaw, dis);
		
		TaskManager.gotoGoal(goal);
		
		ChatUtil.send("Started.");
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return new String[] {"[distance]"};
	}
	
}
