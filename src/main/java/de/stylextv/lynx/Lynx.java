package de.stylextv.lynx;

import de.stylextv.lynx.event.EventBus;
import de.stylextv.lynx.event.listeners.ChatListener;
import de.stylextv.lynx.event.listeners.RenderListener;
import de.stylextv.lynx.event.listeners.TickListener;
import de.stylextv.lynx.event.listeners.WorldListener;
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
		registerListeners();
		
		loadConfigs();
		
		// init
	}
	
	private void registerListeners() {
		EventBus.registerListener(new TickListener());
		EventBus.registerListener(new WorldListener());
		EventBus.registerListener(new RenderListener());
		EventBus.registerListener(new ChatListener());
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
