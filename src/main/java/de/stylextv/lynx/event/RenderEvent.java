package de.stylextv.lynx.event;

import de.stylextv.lynx.input.PlayerContext;
import de.stylextv.lynx.input.controller.MovementController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.scheme.Colors;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvent {
	
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if(!PlayerContext.isInWorld()) return;
		
		if(MovementController.isMoving()) {
			ShapeRenderer.drawPath(event, MovementController.getPath(), Colors.PATH, Colors.PATH_MARKER, 2);
		}
		
		ViewController.onRender();
	}
	
}
