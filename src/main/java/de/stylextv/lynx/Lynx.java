package de.stylextv.lynx;

import de.stylextv.lynx.event.ChatEvent;
import de.stylextv.lynx.event.RenderEvent;
import de.stylextv.lynx.event.TickEvent;
import de.stylextv.lynx.event.WorldEvent;
import de.stylextv.lynx.option.Options;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class Lynx {
	
	private static Lynx instance;
	
	private Options options;
	
	public Lynx() {
		instance = this;
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::setup);
		
		init(bus);
	}
	
	private void init(IEventBus bus) {
		MinecraftForge.EVENT_BUS.register(this);
		
		registerEvents();
		
		options = Options.load();
		
		if(options == null) {
			options = new Options();
			
			options.save();
		}
		
		// init
		
		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
	}
	
	private void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new TickEvent());
		MinecraftForge.EVENT_BUS.register(new WorldEvent());
		MinecraftForge.EVENT_BUS.register(new RenderEvent());
		MinecraftForge.EVENT_BUS.register(new ChatEvent());
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		
	}
	
	private void shutdown() {
		options.save();
	}
	
	public Options getOptions() {
		return options;
	}
	
	public static Lynx getInstance() {
		return instance;
	}
	
}
