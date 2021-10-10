package de.stylextv.maple.event.events;

import de.stylextv.maple.event.Event;
import de.stylextv.maple.event.EventListener;

public class ChunkEvent extends Event {
	
	private Type type;
	
	private int x;
	private int z;
	
	public ChunkEvent(Type type, int x, int z) {
		this.type = type;
		this.x = x;
		this.z = z;
	}
	
	@Override
	public void callListener(EventListener l) {
		if(type == Type.DATA) l.onChunkData(this);
	}
	
	public Type getType() {
		return type;
	}
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
	public enum Type {
		
		DATA;
		
	}
	
}
