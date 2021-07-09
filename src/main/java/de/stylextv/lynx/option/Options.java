package de.stylextv.lynx.option;

import de.stylextv.lynx.config.ConfigHelper;

public class Options {
	
	private static final String FILE_NAME = "options";
	
	public final Option<Boolean> allowBreak = new Option<Boolean>(true);
	
	public final Option<Boolean> allowPlace = new Option<Boolean>(true);
	
	public void save() {
		ConfigHelper.saveFile(FILE_NAME, this);
	}
	
	public static Options load() {
		Options options = ConfigHelper.loadFile(FILE_NAME, Options.class);
		
		return options;
	}
	
}
