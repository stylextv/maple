package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
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
	public int heuristic(Node n) {
		int disX = Math.abs(n.getX() - pos.getX());
		int disY = Math.abs(n.getY() - pos.getY());
		int disZ = Math.abs(n.getZ() - pos.getZ());
		
		int dis = disX + disY + disZ;
		
		return dis * Cost.COST_PER_UNIT;
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
	
}
