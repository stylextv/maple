package de.stylextv.maple.pathing.calc.goal;

import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.render.ShapeRenderer;
import de.stylextv.maple.scheme.Colors;
import net.minecraft.util.math.BlockPos;

public class TwoBlocksGoal extends Goal {
	
	private BlockPos pos;
	
	public TwoBlocksGoal(int x, int y, int z) {
		this(new BlockPos(x, y, z));
	}
	
	public TwoBlocksGoal(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public double heuristic(Node n) {
		int dx = n.getX() - pos.getX();
		int dy = n.getY() - pos.getY();
		int dz = n.getZ() - pos.getZ();
		
		if(dy < 0) dy++;
		
		return BlockGoal.cost(dx, dy, dz);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return x == pos.getX() && (y == pos.getY() || y == pos.getY() - 1) && z == pos.getZ();
	}
	
	@Override
	public void render(RenderWorldEvent event) {
		BlockPos up = pos.up();
		BlockPos down = pos.down();
		
		ShapeRenderer.drawBox(event, up, down, Colors.GOAL, 2);
	}
	
	@Override
	public boolean equals(Goal other) {
		if(!(other instanceof TwoBlocksGoal)) return false;
		
		BlockPos otherPos = ((TwoBlocksGoal) other).getPos();
		
		return otherPos.equals(pos);
	}
	
	@Override
	public String toString() {
		return String.format("TwoBlocksGoal{pos=%s}", pos);
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
}
