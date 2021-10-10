package de.stylextv.maple.event;

import de.stylextv.maple.event.events.BlockUpdateEvent;
import de.stylextv.maple.event.events.ChunkEvent;
import de.stylextv.maple.event.events.ClientChatEvent;
import de.stylextv.maple.event.events.ClientTickEvent;
import de.stylextv.maple.event.events.EntityDeathEvent;
import de.stylextv.maple.event.events.EntitySleptEvent;
import de.stylextv.maple.event.events.InventoryUpdateEvent;
import de.stylextv.maple.event.events.PacketEvent;
import de.stylextv.maple.event.events.RenderTickEvent;
import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.event.events.WorldEvent;

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
