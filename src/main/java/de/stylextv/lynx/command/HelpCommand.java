package de.stylextv.lynx.command;

import de.stylextv.lynx.util.ChatUtil;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help");
	}
	
	@Override
	public void execute(String[] args) {
		ChatUtil.sendToUser("Help o/");
	}
	
}
