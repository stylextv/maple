package de.stylextv.lynx;

import de.stylextv.lynx.cache.WorldCache;
import de.stylextv.lynx.event.GuiEvent;
import de.stylextv.lynx.event.RenderEvent;
import de.stylextv.lynx.event.TickEvent;
import de.stylextv.lynx.event.WorldEvent;
import de.stylextv.lynx.item.ItemManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class Lynx {
	
	private static Lynx instance;
	
	public Lynx() {
		instance = this;
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::setup);
		
		init(bus);
	}
	
	private void init(IEventBus bus) {
		MinecraftForge.EVENT_BUS.register(this);
		
		registerEvents();
		
		ItemManager.register(bus);
		
		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
		
		WorldCache.init();
	}
	
	private void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new TickEvent());
		MinecraftForge.EVENT_BUS.register(new WorldEvent());
		MinecraftForge.EVENT_BUS.register(new RenderEvent());
		MinecraftForge.EVENT_BUS.register(new GuiEvent());
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		
	}
	
	private void shutdown() {
		WorldCache.shutdown();
	}
	
	public static Lynx getInstance() {
		return instance;
	}
	
}
