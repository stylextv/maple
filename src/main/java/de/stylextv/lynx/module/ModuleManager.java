package de.stylextv.lynx.module;

public class ModuleManager {
	
	private static final Module[] MODULES = new Module[] {
			new MiningModule(),
			new CombatModule(),
			new ForagingModule()
	};
	
	public static Module[] getModules() {
		return MODULES;
	}
	
}
