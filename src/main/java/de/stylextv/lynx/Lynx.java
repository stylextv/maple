package de.stylextv.lynx;

import de.stylextv.lynx.memory.waypoint.Waypoints;
import de.stylextv.lynx.option.Options;
import net.fabricmc.api.ModInitializer;

public class Lynx implements ModInitializer {
	
	private static Lynx instance;
	
	private Options options;
	
	public Lynx() {
		instance = this;
	}
	
	@Override
	public void onInitialize() {
		registerEvents();
		
		loadConfigs();
		
		// init
	}
	
	private void registerEvents() {
//		MinecraftForge.EVENT_BUS.register(new TickEvent());
//		MinecraftForge.EVENT_BUS.register(new LevelEvent());
//		MinecraftForge.EVENT_BUS.register(new RenderEvent());
//		MinecraftForge.EVENT_BUS.register(new ChatEvent());
	}
	
	private void loadConfigs() {
		options = Options.load();
		
		if(options == null) {
			options = new Options();
			
			options.save();
		}
		
		Waypoints.load();
	}
	
	public Options getOptions() {
		return options;
	}
	
	public static Lynx getInstance() {
		return instance;
	}
	
}
