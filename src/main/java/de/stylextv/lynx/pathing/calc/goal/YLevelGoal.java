package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;

public class YLevelGoal extends Goal {
	
	private int y;
	
	public YLevelGoal(int y) {
		this.y = y;
	}
	
	@Override
	public double heuristic(Node n) {
		return cost(n.getY(), y);
	}
	
	@Override
	public boolean isFinalNode(Node n) {
		return n.getY() == y;
	}
	
	@Override
	public String toString() {
		return String.format("YGoal{y=%s}", y);
	}
	
	public static double cost(int y, int goalY) {
		if(y < goalY) {
			int dis = goalY - y;
			
			return dis * Cost.JUMP;
		}
		
		if(y > goalY) {
			int dis = y - goalY;
			
			return Cost.FALL_N_BLOCKS[2] / 2 * dis;
		}
		
		return 0;
	}
	
}
