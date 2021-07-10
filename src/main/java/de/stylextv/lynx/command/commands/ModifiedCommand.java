package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.option.Option;
import de.stylextv.lynx.option.Options;
import de.stylextv.lynx.util.ChatUtil;

public class ModifiedCommand extends Command {
	
	public ModifiedCommand() {
		super("modified", "Shows all modified options.");
	}
	
	@Override
	public void execute(String[] args) {
		ChatUtil.sendToUser("Modified options:");
		
		int n = 8;
		
		Option<?>[] options = Options.getOptions();
		
		for(Option<?> option : options) {
			if(option.wasModified()) {
				
				String name = option.getName();
				String type = "Boolean";
				
				String s = String.format("§7%s §8(%s)", name, type);
				
				ChatUtil.sendToUser("- " + s);
				
				n--;
			}
		}
		
		for(int i = 0; i < n; i++) {
			ChatUtil.sendToUser("§8--");
		}
	}
	
}
