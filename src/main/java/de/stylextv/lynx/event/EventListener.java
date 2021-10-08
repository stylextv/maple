package de.stylextv.lynx.event;

import de.stylextv.lynx.event.events.BlockUpdateEvent;
import de.stylextv.lynx.event.events.ChunkEvent;
import de.stylextv.lynx.event.events.ClientChatEvent;
import de.stylextv.lynx.event.events.ClientTickEvent;
import de.stylextv.lynx.event.events.EntityDeathEvent;
import de.stylextv.lynx.event.events.EntitySleptEvent;
import de.stylextv.lynx.event.events.InventoryUpdateEvent;
import de.stylextv.lynx.event.events.PacketEvent;
import de.stylextv.lynx.event.events.RenderTickEvent;
import de.stylextv.lynx.event.events.RenderWorldEvent;
import de.stylextv.lynx.event.events.WorldEvent;

public interface EventListener {
	
	public default void onClientTick(ClientTickEvent event) {}
	
	public default void onRenderTick(RenderTickEvent event) {}
	
	public default void onWorldRender(RenderWorldEvent event) {}
	
	public default void onWorldLoad(WorldEvent event) {}
	
	public default void onWorldUnload(WorldEvent event) {}
	
	public default void onChunkData(ChunkEvent event) {}
	
	public default void onBlockUpdate(BlockUpdateEvent event) {}
	
	public default void onPacketSent(PacketEvent event) {}
	
	public default void onPacketReceived(PacketEvent event) {}
	
	public default void onInventoryUpdate(InventoryUpdateEvent event) {}
	
	public default void onEntityDeath(EntityDeathEvent event) {}
	
	public default void onEntitySlept(EntitySleptEvent event) {}
	
	public default void onClientChat(ClientChatEvent event) {}
	
}
