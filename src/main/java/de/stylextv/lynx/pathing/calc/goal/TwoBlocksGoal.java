package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

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
	public String toString() {
		return String.format("TwoBlocksGoal{pos=%s}", pos);
	}
	
}
