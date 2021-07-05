package de.stylextv.dwarf.pathing.goal;

import de.stylextv.dwarf.pathing.Node;

public interface IGoal {
	
	public abstract int calcHeuristic(Node n);
	
    public abstract boolean isFinalNode(Node n);
	
}
