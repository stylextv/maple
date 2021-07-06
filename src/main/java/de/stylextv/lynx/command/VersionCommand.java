package de.stylextv.lynx.command;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.util.ChatUtil;

public class VersionCommand extends Command {
	
	public VersionCommand() {
		super("version");
	}
	
	@Override
	public void execute(String[] args) {
		String s = Constants.COLORED_NAME + " " + Constants.VERSION + " §7by §e" + Constants.AUTHOR;
		
		ChatUtil.sendToUser("Installed version: " + s);
	}
	
}
