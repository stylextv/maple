package de.stylextv.maple.command.commands;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.pathing.calc.Path;
import de.stylextv.maple.pathing.movement.MovementExecutor;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.util.time.TimeFormat;

public class ETACommand extends Command {
	
	public ETACommand() {
		super("eta", "Displays the estimated time to reach the current destination.");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		if(!MovementExecutor.hasPath()) {
			ChatUtil.send("§cNot actively pathing!");
			
			return true;
		}
		
		Path p = MovementExecutor.getPath();
		
		long time = p.timeToGoal();
		
		String s = TimeFormat.formatDuration(time);
		
		ChatUtil.send("Estimated time until arrival: " + s);
		
		return true;
	}
	
}
