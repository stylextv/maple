package de.stylextv.lynx.event;

import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.input.controller.BuildingController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.memory.waypoint.Waypoint;
import de.stylextv.lynx.memory.waypoint.Waypoints;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.pathing.calc.SearchExecutor;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.render.TextRenderer;
import de.stylextv.lynx.scheme.Colors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvent {
	
	private static final int WAYPOINT_RENDER_DISTANCE = 250 * 250;
	
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if(!WorldContext.isInWorld()) return;
		
		drawCurrentPath(event);
		
		// draw goals
		
		drawPathCalculation(event);
		
		drawWaypoints(event);
		
		if(!WorldContext.isIngame()) return;
		
		MovementExecutor.onRenderTick();
		
		BuildingController.onRenderTick();
		ViewController.onRenderTick();
	}
	
	private void drawCurrentPath(RenderWorldLastEvent event) {
		Path path = MovementExecutor.getPath();
		
		if(path != null) {
			ShapeRenderer.drawPath(event, path, Colors.PATH, Colors.PATH_MARKER, 2);
		}
	}
	
	private void drawPathCalculation(RenderWorldLastEvent event) {
		Node n = SearchExecutor.getCurrentNode();
		
		if(n == null) return;
		
		while(n.getParent() != null) {
			
			Node parent = n.getParent();
			
			float x = n.getX() + 0.5f;
			float y = n.getY() + 0.5f;
			float z = n.getZ() + 0.5f;
			
			float px = parent.getX() + 0.5f;
			float py = parent.getY() + 0.5f;
			float pz = parent.getZ() + 0.5f;
			
			Vector3f[] vertices = new Vector3f[] {
					new Vector3f(x, y, z),
					new Vector3f(px, py, pz)
			};
			
			ShapeRenderer.drawLine(event, vertices, Colors.PATH_CALCULATION, 2);
			
			n = parent;
		}
	}
	
	private void drawWaypoints(RenderWorldLastEvent event) {
		for(Waypoint p : Waypoints.getWaypoints()) {
			drawWaypoint(event, p);
		}
	}
	
	private void drawWaypoint(RenderWorldLastEvent event, Waypoint p) {
		if(!p.isInWorld()) return;
		
		double dis = p.distanceSqr();
		
		if(dis > WAYPOINT_RENDER_DISTANCE) return;
		
		BlockPos pos = p.getPos();
		
		float x = pos.getX() + 0.5f;
		float y = pos.getY() + 1.4f;
		float z = pos.getZ() + 0.5f;
		
		Vector3f v1 = new Vector3f(x, pos.getY(), z);
		Vector3f v2 = new Vector3f(x, 1024, z);
		
		Vector3f[] vertices = new Vector3f[] {
			v1, v2
		};
		
		ShapeRenderer.drawLine(event, vertices, Colors.WAYPOINT, 2);
		
		String s = "§f" + p.getName();
		
		TextRenderer.drawText(event, s, x, y, z);
	}
	
}
