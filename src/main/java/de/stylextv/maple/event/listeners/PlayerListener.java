package de.stylextv.maple.event.listeners;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.event.EventListener;
import de.stylextv.maple.event.events.EntityDeathEvent;
import de.stylextv.maple.event.events.EntitySleptEvent;
import de.stylextv.maple.event.events.InventoryUpdateEvent;
import de.stylextv.maple.event.events.PacketEvent;
import de.stylextv.maple.gui.ToolSet;
import de.stylextv.maple.memory.waypoint.WaypointTag;
import de.stylextv.maple.memory.waypoint.Waypoints;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class PlayerListener implements EventListener {
	
	@Override
	public void onInventoryUpdate(InventoryUpdateEvent event) {
		if(!WorldContext.isInWorld()) return;
		
		DefaultedList<ItemStack> list = event.getList();
		
		PlayerInventory inv = PlayerContext.inventory();
		
		if(list.equals(inv.main)) ToolSet.updateTools();
	}
	
	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		if(!WorldContext.isInWorld()) return;
		
		LivingEntity entity = event.getEntity();
		
		ClientPlayerEntity p = PlayerContext.player();
		
		if(p.equals(entity)) {
			
			String levelName = WorldContext.getLevelName();
			
			BlockPos pos = entity.getBlockPos();
			
			Waypoints.addWaypoint(WaypointTag.DEATH, levelName, pos);
		}
	}
	
	@Override
	public void onEntitySlept(EntitySleptEvent event) {
		if(!WorldContext.isInWorld()) return;
		
		LivingEntity entity = event.getEntity();
		
		ClientPlayerEntity p = PlayerContext.player();
		
		if(p.equals(entity)) {
			
			String levelName = WorldContext.getLevelName();
			
			BlockPos pos = entity.getBlockPos();
			
			Waypoints.addWaypoint(WaypointTag.BED, levelName, pos);
		}
	}
	
	@Override
	public void onPacketSent(PacketEvent event) {
		Packet<?> packet = event.getPacket();
		
		if(packet instanceof PlayerActionC2SPacket) {
			
			PlayerActionC2SPacket actionPacket = (PlayerActionC2SPacket) packet;
			
			Action action = actionPacket.getAction();
			
			boolean isDrop = action == Action.DROP_ITEM || action == Action.DROP_ALL_ITEMS;
			
			if(isDrop) ToolSet.updateTools();
		}
	}
	
}
