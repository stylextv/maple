package de.stylextv.lynx.event;

import de.stylextv.lynx.event.events.ClientChatEvent;
import de.stylextv.lynx.event.events.ClientTickEvent;
import de.stylextv.lynx.event.events.RenderTickEvent;
import de.stylextv.lynx.event.events.RenderWorldEvent;
import de.stylextv.lynx.event.events.WorldLoadEvent;
import de.stylextv.lynx.event.events.WorldUnloadEvent;

public interface EventListener {
	
	public default void onClientTick(ClientTickEvent event) {}
	
	public default void onRenderTick(RenderTickEvent event) {}
	
	public default void onWorldRender(RenderWorldEvent event) {}
	
	public default void onWorldLoad(WorldLoadEvent event) {}
	
	public default void onWorldUnload(WorldUnloadEvent event) {}
	
	public default void onClientChat(ClientChatEvent event) {}
	
}
