package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.command.ArgumentHelper;
import de.stylextv.lynx.pathing.calc.Node;

public abstract class Goal {
	
	private static final String[] USAGES = new String[] {
			"<x> <y> <z>"
	};
	
	public abstract int heuristic(Node n);
	
	public abstract boolean isFinalNode(Node n);
	
	public static Goal fromArgs(String[] args) {
		if(args.length != 3) return null;
		
		Integer x = ArgumentHelper.toInt(args[0]);
		Integer y = ArgumentHelper.toInt(args[1]);
		Integer z = ArgumentHelper.toInt(args[2]);
		
		if(x == null || y == null || z == null) return null;
		return new BlockGoal(x, y, z);
	}
	
	public static String[] getUsages() {
		return USAGES;
	}
	
}
