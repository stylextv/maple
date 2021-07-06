package de.stylextv.lynx.command;

import de.stylextv.lynx.util.ChatUtil;

public class CommandManager {
	
	private static final char COMMAND_PREFIX = '#';
	
	private static final Command[] COMMANDS = new Command[] {
			new HelpCommand(),
			new GoalCommand(),
			new StartCommand(),
			new StopCommand(),
			new VersionCommand()
	};
	
	public static void parseMessage(String s) {
		s = s.substring(1);
		
		ChatUtil.sendToUser("§f> " + s);
		
		String[] split = s.split(" ");
		
		String name = split[0];
		
		String[] args = new String[split.length - 1];
		
		for(int i = 0; i < args.length; i++) {
			args[i] = split[i + 1];
		}
		
		Command c = getCommand(name);
		
		if(c == null) {
			ChatUtil.sendToUser("Couldn't find command!");
			
			return;
		}
		
		c.execute(args);
	}
	
	public static boolean isCommandMessage(String s) {
		return s.startsWith("" + COMMAND_PREFIX);
	}
	
	public static Command getCommand(String name) {
		for(Command c : COMMANDS) {
			if(c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		
		return null;
	}
	
}
