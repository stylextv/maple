package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.event.events.RenderWorldEvent;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.scheme.Colors;
import net.minecraft.util.math.BlockPos;

public class XZGoal extends Goal {
	
	private int x;
	private int z;
	
	public XZGoal(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	@Override
	public double heuristic(Node n) {
		int dx = n.getX() - x;
		int dz = n.getZ() - z;
		
		return cost(dx, dz);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return n.getX() == x && n.getZ() == z;
	}
	
	@Override
	public void render(RenderWorldEvent event) {
		BlockPos pos1 = new BlockPos(x, 0, z);
		BlockPos pos2 = new BlockPos(x, 257, z);
		
		ShapeRenderer.drawBox(event, pos1, pos2, Colors.GOAL, 2);
	}
	
	@Override
	public String toString() {
		return String.format("XZGoal{x=%s, z=%s}", x, z);
	}
	
	public static double cost(int dx, int dz) {
		int x = Math.abs(dx);
		int z = Math.abs(dz);
		
		int straight;
		int diagonal;
		
		if(x < z) {
			
			straight = z - x;
			diagonal = x;
			
		} else {
			
			straight = x - z;
			diagonal = z;
		}
		
		return Cost.SPRINT_STRAIGHT * straight + Cost.SPRINT_DIAGONALLY * diagonal;
	}
	
}
