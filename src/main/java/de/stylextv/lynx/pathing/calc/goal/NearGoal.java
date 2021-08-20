package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

public class NearGoal extends Goal {
	
	private BlockPos pos;
	
	private float dis;
	
	public NearGoal(int x, int y, int z, float dis) {
		this(new BlockPos(x, y, z), dis);
	}
	
	public NearGoal(BlockPos pos, float dis) {
		this.pos = pos;
		this.dis = dis * dis;
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
		int disX = n.getX() - pos.getX();
		int disY = n.getY() - pos.getY();
		int disZ = n.getZ() - pos.getZ();
		
		int d = disX * disX + disY * disY + disZ * disZ;
		
		return d <= dis;
	}
	
	@Override
	public String toString() {
		return String.format("NearGoal{pos=%s, dis=%s}", pos, dis);
	}
	
}
