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
		int dx = n.getX() - pos.getX();
		int dy = n.getY() - pos.getY();
		int dz = n.getZ() - pos.getZ();
		
		return BlockGoal.cost(dx, dy, dz);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return n.distanceSqr(pos) <= dis;
	}
	
	@Override
	public String toString() {
		return String.format("NearGoal{pos=%s, dis=%s}", pos, dis);
	}
	
}
