package de.stylextv.lynx.pathing.goal;

import de.stylextv.lynx.pathing.Node;

public interface IGoal {
	
	public abstract int calcHeuristic(Node n);
	
    public abstract boolean isFinalNode(Node n);
	
}
