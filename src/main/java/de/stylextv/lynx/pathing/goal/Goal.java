package de.stylextv.lynx.pathing.goal;

import de.stylextv.lynx.pathing.Node;

public abstract class Goal {
	
	public abstract int heuristic(Node n);
	
	public abstract boolean isFinalNode(Node n);
	
}
