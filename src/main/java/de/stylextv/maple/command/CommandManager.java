package de.stylextv.maple.command;

import de.stylextv.maple.command.commands.AxisCommand;
import de.stylextv.maple.command.commands.ETACommand;
import de.stylextv.maple.command.commands.FarmCommand;
import de.stylextv.maple.command.commands.FollowCommand;
import de.stylextv.maple.command.commands.GotoCommand;
import de.stylextv.maple.command.commands.HelpCommand;
import de.stylextv.maple.command.commands.HomeCommand;
import de.stylextv.maple.command.commands.LostCommand;
import de.stylextv.maple.command.commands.MineCommand;
import de.stylextv.maple.command.commands.ModifiedCommand;
import de.stylextv.maple.command.commands.PauseCommand;
import de.stylextv.maple.command.commands.ResumeCommand;
import de.stylextv.maple.command.commands.StopCommand;
import de.stylextv.maple.command.commands.ThisWayCommand;
import de.stylextv.maple.command.commands.TunnelCommand;
import de.stylextv.maple.command.commands.VersionCommand;
import de.stylextv.maple.command.commands.WaypointCommand;
import de.stylextv.maple.util.ExceptionUtil;
import de.stylextv.maple.util.chat.ChatUtil;

public class CommandManager {
	
	private static final char COMMAND_PREFIX = '#';
	
	private static final Command[] COMMANDS = new Command[] {
			new HelpCommand(),
			new GotoCommand(),
			new MineCommand(),
			new FarmCommand(),
			new FollowCommand(),
			// TODO other task commands
			new StopCommand(),
			new PauseCommand(),
			new ResumeCommand(),
			new WaypointCommand(),
			new HomeCommand(),
			new LostCommand(),
			new TunnelCommand(),
			new ThisWayCommand(),
			new AxisCommand(),
			new ETACommand(),
			new ModifiedCommand(),
			new VersionCommand()
	};
	
	public static void parseMessage(String s) {
		s = s.substring(1);
		
		ChatUtil.send("§f> " + s);
		
		String[] split = s.split(" ", 2);
		
		String name = split[0];
		
		Command c = getCommand(name);
		
		if(c == null) {
			ChatUtil.send("§cCouldn't find command!");
			
			return;
		}
		
		String args = "";
		
		if(split.length > 1) args = split[1];
		
		ArgumentList list = ArgumentList.fromString(args);
		
		ExceptionUtil.catchEverything(() -> {
			
			boolean success = c.execute(list);
			
			if(!success) {
				ChatUtil.send("§cInvalid arguments!");
			}
			
		}, () -> ChatUtil.send("§cAn error has occurred!"));
	}
	
	public static boolean isCommandMessage(String s) {
		return s.startsWith("" + COMMAND_PREFIX);
	}
	
	public static Command getCommand(String name) {
		for(Command c : COMMANDS) {
			if(c.nameEquals(name)) return c;
		}
		
		return null;
	}
	
	public static Command[] getCommands() {
		return COMMANDS;
	}
	
}
