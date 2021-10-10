package de.stylextv.maple.event.events;

import de.stylextv.maple.event.Event;
import de.stylextv.maple.event.EventListener;
import net.minecraft.client.util.math.MatrixStack;

public class RenderWorldEvent extends Event {
	
	private MatrixStack matrixStack;
	
	public RenderWorldEvent(MatrixStack stack) {
		this.matrixStack = stack;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onWorldRender(this);
	}
	
	public MatrixStack getMatrixStack() {
		return matrixStack;
	}
	
}
