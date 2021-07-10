package de.stylextv.lynx.option;

import de.stylextv.lynx.config.ConfigHelper;

public class Options {
	
	private static final String FILE_NAME = "options";
	
	private static final Option<?>[] OPTIONS = new Option[2];
	
	private static int pointer;
	
	public final Option<Boolean> allowBreak = new Option<Boolean>("allowBreak", true);
	
	public final Option<Boolean> allowPlace = new Option<Boolean>("allowPlace", true);
	
	public void save() {
		ConfigHelper.saveFile(FILE_NAME, this);
	}
	
	public static Options load() {
		Options options = ConfigHelper.loadFile(FILE_NAME, Options.class);
		
		return options;
	}
	
	public static void registerOption(Option<?> option) {
		OPTIONS[pointer] = option;
		
		pointer++;
	}
	
	public static Option<?>[] getOptions() {
		return OPTIONS;
	}
	
}
