package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.command.CommandManager;
import de.stylextv.lynx.util.ChatUtil;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "Gives useful information about commands.");
	}
	
	@Override
	public void execute(String[] args) {
		if(args.length == 0) {
			ChatUtil.sendToUser("Use §o#help <command> §7to get help for individual commands.", "", "Commands:");
			
			for(Command c : CommandManager.getCommands()) {
				String name = c.getName();
				
				ChatUtil.sendToUser(" - " + name);
			}
			
			return;
		}
		
		Command c = CommandManager.getCommand(args[0]);
		
		if(c == null) {
			ChatUtil.sendToUser("§cCouldn't find command!");
			
			return;
		}
		
		String name = c.getName();
		String description = c.getDescription();
		
		ChatUtil.sendToUser("#" + name + ":", description);
		
		String[] usages = c.getUsages();
		
		if(usages == null) return;
		
		ChatUtil.sendToUser("", "Usages:");
		
		for(String s : usages) {
			ChatUtil.sendToUser(" - " + name + " " + s);
		}
	}
	
	@Override
	public String[] getUsages() {
		return new String[] {"[command]"};
	}
	
}
