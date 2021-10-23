package de.stylextv.maple.event.events;

import de.stylextv.maple.event.Event;
import de.stylextv.maple.event.EventListener;
import net.minecraft.client.util.math.MatrixStack;

public class RenderWorldEvent extends Event {
	
	private Type type;
	
	private MatrixStack matrixStack;
	
	public RenderWorldEvent(Type type, MatrixStack stack) {
		this.type = type;
		this.matrixStack = stack;
	}
	
	@Override
	public void callListener(EventListener l) {
		if(type == Type.ENTITIES) l.onEntitiesRender(this);
	}
	
	public Type getType() {
		return type;
	}
	
	public MatrixStack getMatrixStack() {
		return matrixStack;
	}
	
	public enum Type {
		
		ENTITIES;
		
	}
	
}
