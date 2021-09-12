package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;
import net.minecraft.client.world.ClientWorld;

public class WorldUnloadEvent extends Event {
	
	private ClientWorld world;
	
	public WorldUnloadEvent(ClientWorld world) {
		this.world = world;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onWorldUnload(this);
	}
	
	public ClientWorld getWorld() {
		return world;
	}
	
}
