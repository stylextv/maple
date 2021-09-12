package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;
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
