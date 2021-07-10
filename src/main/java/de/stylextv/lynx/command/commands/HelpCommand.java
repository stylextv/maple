package de.stylextv.lynx.command.commands;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.command.ArgumentHelper;
import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.command.CommandManager;
import de.stylextv.lynx.util.ChatUtil;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "Gives useful information about commands.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Integer page = null;
		
		if(args.length == 0) {
			page = 0;
		} else {
			page = ArgumentHelper.toInt(args[0]);
		}
		
		if(page != null) {
			ChatUtil.send("Use §o#help <command> §7to get help for individual commands.", "", "Commands:");
			
			List<String> list = new ArrayList<>();
			
			for(Command c : CommandManager.getCommands()) {
				String name = c.getName();
				
				list.add(name);
			}
			
			ChatUtil.sendList(list, 6, page, getName());
			
			return true;
		}
		
		Command c = CommandManager.getCommand(args[0]);
		
		if(c == null) {
			ChatUtil.send("§cCouldn't find command!");
			
			return true;
		}
		
		String name = c.getName();
		String description = c.getDescription();
		
		ChatUtil.send("#" + name + ":", description);
		
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
