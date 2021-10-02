package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.event.events.RenderWorldEvent;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.render.ShapeRenderer;
import de.stylextv.lynx.scheme.Colors;
import net.minecraft.util.math.BlockPos;

public class BlockGoal extends Goal {
	
	private BlockPos pos;
	
	public BlockGoal(int x, int y, int z) {
		this(new BlockPos(x, y, z));
	}
	
	public BlockGoal(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public double heuristic(Node n) {
		int dx = n.getX() - pos.getX();
		int dy = n.getY() - pos.getY();
		int dz = n.getZ() - pos.getZ();
		
		return cost(dx, dy, dz);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return x == pos.getX() && y == pos.getY() && z == pos.getZ();
	}
	
	@Override
	public void render(RenderWorldEvent event) {
		BlockPos up = pos.up();
		
		ShapeRenderer.drawBox(event, pos, up, Colors.GOAL, 2);
	}
	
	@Override
	public String toString() {
		return String.format("BlockGoal{pos=%s}", pos);
	}
	
	public static double cost(int dx, int dy, int dz) {
		double cost = XZGoal.cost(dx, dz);
		
		cost += YLevelGoal.cost(dy, 0);
		
		return cost;
	}
	
}
