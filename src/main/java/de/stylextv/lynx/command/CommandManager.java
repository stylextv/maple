package de.stylextv.lynx.command;

import de.stylextv.lynx.command.commands.AxisCommand;
import de.stylextv.lynx.command.commands.GoCommand;
import de.stylextv.lynx.command.commands.GoalCommand;
import de.stylextv.lynx.command.commands.GotoCommand;
import de.stylextv.lynx.command.commands.HelpCommand;
import de.stylextv.lynx.command.commands.HomeCommand;
import de.stylextv.lynx.command.commands.InvertCommand;
import de.stylextv.lynx.command.commands.LostCommand;
import de.stylextv.lynx.command.commands.ModifiedCommand;
import de.stylextv.lynx.command.commands.StopCommand;
import de.stylextv.lynx.command.commands.VersionCommand;
import de.stylextv.lynx.command.commands.WaypointCommand;
import de.stylextv.lynx.util.chat.ChatUtil;

public class CommandManager {
	
	private static final char COMMAND_PREFIX = '#';
	
	private static final Command[] COMMANDS = new Command[] {
			new HelpCommand(),
			new GotoCommand(),
			new GoalCommand(),
			new GoCommand(),
			new StopCommand(),
			new WaypointCommand(),
			new HomeCommand(),
			new LostCommand(),
			new AxisCommand(),
			new InvertCommand(),
			new ModifiedCommand(),
			new VersionCommand()
	};
	
	public static void parseMessage(String s) {
		s = s.substring(1);
		
		ChatUtil.send("§f> " + s);
		
		String[] split = s.split(" ");
		
		String name = split[0];
		
		String[] args = new String[split.length - 1];
		
		for(int i = 0; i < args.length; i++) {
			args[i] = split[i + 1];
		}
		
		Command c = getCommand(name);
		
		if(c == null) {
			ChatUtil.send("§cCouldn't find command!");
			
			return;
		}
		
		boolean success = c.execute(args);
		
		if(!success) {
			ChatUtil.send("§cInvalid arguments!");
		}
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
