package de.stylextv.dwarf.event;

import de.stylextv.dwarf.input.PlayerContext;
import de.stylextv.dwarf.input.controller.MovementController;
import de.stylextv.dwarf.input.controller.ViewController;
import de.stylextv.dwarf.render.ShapeRenderer;
import de.stylextv.dwarf.scheme.Colors;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvent {
	
	@SubscribeEvent
	public void onRender(RenderWorldLastEvent event) {
		if(!PlayerContext.isInWorld()) return;
		
		if(MovementController.isMoving()) {
			ShapeRenderer.drawPath(event, MovementController.getPath(), Colors.PATH, Colors.PATH_MARKER, 2);
		}
		
		ViewController.onRender();
	}
	
}
