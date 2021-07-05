package de.stylextv.lynx.pathing.goal;

import de.stylextv.lynx.pathing.Node;
import net.minecraft.util.math.BlockPos;

public class CloseGoal implements IGoal {
	
	private BlockPos pos;
	
	public CloseGoal(BlockPos pos) {
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
		int disX = Math.abs(n.getX() - pos.getX());
		int disY = Math.abs(n.getY() - pos.getY());
		int disZ = Math.abs(n.getZ() - pos.getZ());
		
		int dis = disX + disY + disZ;
		
		return dis < 3;
	}
	
}
