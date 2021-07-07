package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.util.ChatUtil;

public class VersionCommand extends Command {
	
	public VersionCommand() {
		super("version", "Shows the installed version of LYNX.");
	}
	
	@Override
	public void execute(String[] args) {
		String s = Constants.COLORED_NAME + " " + Constants.VERSION + " §7by §e" + Constants.AUTHOR;
		
		ChatUtil.sendToUser("Installed: " + s);
	}
	
}
