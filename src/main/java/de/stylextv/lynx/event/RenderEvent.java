package de.stylextv.lynx.event;

import de.stylextv.lynx.input.PlayerContext;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.scheme.Colors;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvent {
	
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if(!PlayerContext.isInWorld()) return;
		
		Path path = MovementExecutor.getPath();
		
		if(path != null) {
			ShapeRenderer.drawPath(event, path, Colors.PATH, Colors.PATH_MARKER, 2);
		}
		
		// draw goals, path calculation and waypoints
		
		if(!PlayerContext.isIngame()) return;
		
		MovementExecutor.onRenderTick();
		
		ViewController.onRenderTick();
	}
	
}
