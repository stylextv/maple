package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.util.chat.ChatUtil;
import de.stylextv.lynx.util.time.TimeFormat;

public class ETACommand extends Command {
	
	public ETACommand() {
		super("eta", "Displays the estimated time to reach the current destination.");
	}
	
	@Override
	public boolean execute(String[] args) {
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
