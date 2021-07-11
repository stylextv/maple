package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.cost.Cost;
import net.minecraft.util.math.BlockPos;

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
	public int heuristic(Node n) {
		int disX = Math.abs(n.getX() - pos.getX());
		int disY = Math.abs(n.getY() - pos.getY());
		int disZ = Math.abs(n.getZ() - pos.getZ());
		
		int dis = disX + disY + disZ;
		
		return dis * Cost.COST_PER_UNIT;
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
