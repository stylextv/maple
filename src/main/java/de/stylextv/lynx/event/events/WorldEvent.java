package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;
import net.minecraft.client.world.ClientWorld;

public class WorldEvent extends Event {
	
	private Type type;
	
	private ClientWorld world;
	
	public WorldEvent(Type type, ClientWorld world) {
		this.type = type;
		this.world = world;
	}
	
	@Override
	public void callListener(EventListener l) {
		if(type == Type.LOAD) {
			
			l.onWorldLoad(this);
			
		} else {
			
			l.onWorldUnload(this);
		}
	}
	
	public Type getType() {
		return type;
	}
	
	public ClientWorld getWorld() {
		return world;
	}
	
	public enum Type {
		
		LOAD, UNLOAD;
		
	}
	
}
