package de.stylextv.lynx.event;

import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.memory.waypoint.Waypoint;
import de.stylextv.lynx.memory.waypoint.Waypoints;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.pathing.calc.PathSegment;
import de.stylextv.lynx.pathing.calc.SearchExecutor;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.render.TextRenderer;
import de.stylextv.lynx.scheme.Colors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvent {
	
	private static final int BEAM_RENDER_DISTANCE = 250 * 250;
	
	private static final int BEAM_HEIGHT = 1024;
	
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if(!LevelContext.isInLevel()) return;
		
		drawPath(event, MovementExecutor.getPath());
		
		// TODO draw goals
		
		drawPathCalculation(event);
		
		drawWaypoints(event);
	}
	
	private void drawPath(RenderWorldLastEvent event, Path path) {
		if(path == null) return;
		
		for(PathSegment s : path.getAllSegments()) {
			drawPathSegment(event, s);
		}
	}
	
	private void drawPathSegment(RenderWorldLastEvent event, PathSegment s) {
		ShapeRenderer.drawPathSegment(event, s, Colors.PATH, Colors.PATH_MARKER, 2);
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
			
			Vec3[] vertices = new Vec3[] {
					new Vec3(x, y, z),
					new Vec3(px, py, pz)
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
		
		if(dis > BEAM_RENDER_DISTANCE) return;
		
		BlockPos pos = p.getPos();
		
		float x = pos.getX() + 0.5f;
		float y = pos.getY() + 1.4f;
		float z = pos.getZ() + 0.5f;
		
		Vec3 v1 = new Vec3(x, pos.getY(), z);
		Vec3 v2 = new Vec3(x, BEAM_HEIGHT, z);
		
		Vec3[] vertices = new Vec3[] {
			v1, v2
		};
		
		ShapeRenderer.drawLine(event, vertices, Colors.WAYPOINT, 2);
		
		String s = "§f" + p.getName();
		
		TextRenderer.drawText(event, s, x, y, z);
	}
	
}
