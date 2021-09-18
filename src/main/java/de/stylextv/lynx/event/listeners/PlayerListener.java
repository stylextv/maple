package de.stylextv.lynx.event.listeners;

import de.stylextv.lynx.event.EventListener;
import de.stylextv.lynx.event.events.PacketEvent;
import de.stylextv.lynx.gui.ToolSet;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;

public class PlayerListener implements EventListener {
	
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
