package de.stylextv.lynx.pathing.goal;

import de.stylextv.lynx.pathing.Node;
import net.minecraft.util.math.BlockPos;

public class BlockGoal implements IGoal {
	
	private BlockPos pos;
	
	public BlockGoal(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public int calcHeuristic(Node n) {
		int disX = Math.abs(n.getX() - pos.getX());
		int disY = Math.abs(n.getY() - pos.getY());
		int disZ = Math.abs(n.getZ() - pos.getZ());
		
		int dis = disX + disY + disZ;
		
		return dis * Node.COST_PER_UNIT;
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return x == pos.getX() && y == pos.getY() && z == pos.getZ();
	}
	
}
