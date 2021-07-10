package de.stylextv.lynx.event;

import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.memory.waypoint.Waypoint;
import de.stylextv.lynx.memory.waypoint.Waypoints;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.render.TextRenderer;
import de.stylextv.lynx.scheme.Colors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvent {
	
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if(!WorldContext.isInWorld()) return;
		
		drawCurrentPath(event);
		
		// draw goals, path calculation
		
		drawWaypoints(event);
		
		if(!WorldContext.isIngame()) return;
		
		MovementExecutor.onRenderTick();
		
		ViewController.onRenderTick();
	}
	
	private void drawCurrentPath(RenderWorldLastEvent event) {
		Path path = MovementExecutor.getPath();
		
		if(path != null) {
			ShapeRenderer.drawPath(event, path, Colors.PATH, Colors.PATH_MARKER, 2);
		}
	}
	
	private void drawWaypoints(RenderWorldLastEvent event) {
		for(Waypoint p : Waypoints.getWaypoints()) {
			drawWaypoint(event, p);
		}
	}
	
	private void drawWaypoint(RenderWorldLastEvent event, Waypoint p) {
		if(!p.isInWorld()) return;
		
		BlockPos pos = p.getPos();
		
		float x = pos.getX() + 0.5f;
		float y = pos.getY() + 1.4f;
		float z = pos.getZ() + 0.5f;
		
		Vector3f v1 = new Vector3f(x, 0, z);
		Vector3f v2 = new Vector3f(x, 256, z);
		
		Vector3f[] vertices = new Vector3f[] {
			v1, v2
		};
		
		ShapeRenderer.drawLine(event, vertices, Colors.WAYPOINT, 2);
		
		String s = "§f" + p.getName();
		
		TextRenderer.drawText(event, s, x, y, z);
	}
	
}
