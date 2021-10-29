package de.stylextv.maple.command.commands;

import de.stylextv.maple.Constants;
import de.stylextv.maple.command.ArgumentList;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.util.chat.ChatUtil;

public class VersionCommand extends Command {
	
	public VersionCommand() {
		super("version", "Shows the installed version of Maple.", "ver", "v");
	}
	
	@Override
	public boolean execute(ArgumentList args) {
		String s = Constants.COLORED_NAME + " " + Constants.VERSION + " §7by §e" + Constants.AUTHOR;
		
		ChatUtil.send("Installed: " + s);
		
		return true;
	}
	
}
