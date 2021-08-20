package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

public class GetToBlockGoal extends Goal {
	
	private BlockPos pos;
	
	public GetToBlockGoal(int x, int y, int z) {
		this(new BlockPos(x, y, z));
	}
	
	public GetToBlockGoal(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public double heuristic(Node n) {
		int diffX = n.getX() - pos.getX();
		int diffY = n.getY() - pos.getY();
		int diffZ = n.getZ() - pos.getZ();
		
		return BlockGoal.cost(diffX, diffY, diffZ);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		int disX = Math.abs(n.getX() - pos.getX());
		int disY = Math.abs(n.getY() - pos.getY());
		int disZ = Math.abs(n.getZ() - pos.getZ());
		
		int dis = disX + disY + disZ;
		
		return dis < 2;
	}
	
	@Override
	public String toString() {
		return String.format("GetToBlockGoal{pos=%s}", pos);
	}
	
}
