package de.stylextv.maple;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.listeners.ChatListener;
import de.stylextv.maple.event.listeners.PlayerListener;
import de.stylextv.maple.event.listeners.RenderListener;
import de.stylextv.maple.event.listeners.TickListener;
import de.stylextv.maple.event.listeners.WorldListener;
import de.stylextv.maple.option.Options;
import de.stylextv.maple.waypoint.Waypoints;
import net.fabricmc.api.ModInitializer;

public class Maple implements ModInitializer {
	
	private static Maple instance;
	
	private Options options;
	
	public Maple() {
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
		EventBus.registerListener(new PlayerListener());
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
	
	public static Maple getInstance() {
		return instance;
	}
	
}
