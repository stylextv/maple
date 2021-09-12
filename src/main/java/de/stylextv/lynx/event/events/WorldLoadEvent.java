package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;
import net.minecraft.client.world.ClientWorld;

public class WorldLoadEvent extends Event {
	
	private ClientWorld world;
	
	public WorldLoadEvent(ClientWorld world) {
		this.world = world;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onWorldLoad(this);
	}
	
	public ClientWorld getWorld() {
		return world;
	}
	
}
