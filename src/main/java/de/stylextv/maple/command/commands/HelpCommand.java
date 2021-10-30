package de.stylextv.maple.command.commands;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.command.CommandManager;
import de.stylextv.maple.util.TextUtil;
import de.stylextv.maple.util.chat.ChatUtil;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "Gives useful information about commands.");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		boolean b = args.hasAtLeast(1);
		
		if(!b || args.isInt(0)) {
			
			int page = b ? args.getInt(0) : 1;
			
			ChatUtil.send("Use §o#help <command> §7to get help for individual commands.", "", "Commands:");
			
			List<String> list = new ArrayList<>();
			
			for(Command c : CommandManager.getCommands()) {
				String name = c.getName();
				
				list.add(name);
			}
			
			ChatUtil.sendList(list, 4, page, getName());
			
			return true;
		}
		
		String s = args.get(0);
		
		Command c = CommandManager.getCommand(s);
		
		if(c == null) {
			ChatUtil.send("§cCouldn't find command!");
			
			return true;
		}
		
		String name = c.getName();
		String description = c.getDescription();
		String[] aliases = c.getAliases();
		
		ChatUtil.send("#" + name + ":", description);
		
		if(aliases.length != 0) {
			
			String s2 = TextUtil.combine(aliases, ", ");
			
			ChatUtil.send("Aliases: " + s2);
		}
		
		String[] usages = c.getUsages();
		
		if(usages == null) return true;
		
		ChatUtil.send("", "Usages:");
		
		for(String s2 : usages) {
			ChatUtil.send(" - " + name + " " + s2);
		}
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return new String[] {"[page]", "[command]"};
	}
	
}
