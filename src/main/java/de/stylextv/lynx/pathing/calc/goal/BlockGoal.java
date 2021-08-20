package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

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
		int diffX = n.getX() - pos.getX();
		int diffY = n.getY() - pos.getY();
		int diffZ = n.getZ() - pos.getZ();
		
		return cost(diffX, diffY, diffZ);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return x == pos.getX() && y == pos.getY() && z == pos.getZ();
	}
	
	@Override
	public String toString() {
		return String.format("BlockGoal{pos=%s}", pos);
	}
	
	public static double cost(int diffX, int diffY, int diffZ) {
		double cost = XZGoal.cost(diffX, diffZ);
		
		cost += YLevelGoal.cost(diffY, 0);
		
		return cost;
	}
	
}
