package de.stylextv.lynx.pathing.goal;

import de.stylextv.lynx.pathing.Cost;
import de.stylextv.lynx.pathing.Node;
import net.minecraft.util.math.BlockPos;

public class TwoBlocksGoal extends Goal {
	
	private BlockPos pos;
	
	public TwoBlocksGoal(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public int heuristic(Node n) {
		int disX = Math.abs(n.getX() - pos.getX());
		int disY = Math.abs(n.getY() - pos.getY());
		int disZ = Math.abs(n.getZ() - pos.getZ());
		
		if(disY < 0) disY++;
		
		int dis = disX + disY + disZ;
		
		return dis * Cost.COST_PER_UNIT;
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
		return "TwoBlocksGoal{pos=" + pos + "}";
	}
	
}
