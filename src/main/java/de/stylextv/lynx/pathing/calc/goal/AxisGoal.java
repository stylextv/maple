package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.cost.Cost;

public class AxisGoal extends Goal {
	
	@Override
	public int heuristic(Node n) {
		int x = Math.abs(n.getX());
		int z = Math.abs(n.getZ());
		
		int majorDis = Math.min(x, z);
		
		int minorDis = Math.abs(x - z);
		
		return Math.min(majorDis, minorDis) * Cost.COST_PER_UNIT;
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
