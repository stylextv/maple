package de.stylextv.lynx.pathing.goal;

import de.stylextv.lynx.pathing.Cost;
import de.stylextv.lynx.pathing.Node;

public class AxisGoal extends Goal {
	
	@Override
	public int heuristic(Node n) {
		int x = Math.abs(n.getX());
		int z = Math.abs(n.getZ());
		
		int dis = Math.min(x, z);
		
		return dis * Cost.COST_PER_UNIT;
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		int x = n.getX();
		int z = n.getZ();
		
		return x == 0 || z == 0 || Math.abs(x) == Math.abs(z);
	}
	
	@Override
	public String toString() {
		return "AxisGoal";
	}
	
}
