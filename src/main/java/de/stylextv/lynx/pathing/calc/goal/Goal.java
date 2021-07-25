package de.stylextv.lynx.pathing.calc.goal;

import de.stylextv.lynx.command.ArgumentHelper;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

public abstract class Goal {
	
	private static final String[] USAGES = new String[] {
			"<x> <y> <z> [radius]",
			"<x> <z>",
			"<y>"
	};
	
	public abstract int heuristic(Node n);
	
	public abstract boolean isFinalNode(Node n);
	
	public static Goal fromArgs(String[] args) {
		if(args.length == 0) {
			BlockPos pos = PlayerContext.feetPosition();
			
			return new BlockGoal(pos);
		}
		
		if(args.length >= 3) {
			
			Integer x = ArgumentHelper.toCoordinate(args[0], 0);
			Integer y = ArgumentHelper.toCoordinate(args[1], 1);
			Integer z = ArgumentHelper.toCoordinate(args[2], 2);
			
			if(x == null || y == null || z == null) return null;
			
			if(args.length >= 4) {
				Float dis = ArgumentHelper.toFloat(args[3]);
				
				if(dis == null) return null;
				return new NearGoal(x, y, z, dis);
			}
			
			return new BlockGoal(x, y, z);
		}
		
		if(args.length == 2) {
			
			Integer x = ArgumentHelper.toCoordinate(args[0], 0);
			Integer z = ArgumentHelper.toCoordinate(args[1], 2);
			
			if(x == null || z == null) return null;
			return new XZGoal(x, z);
		}
		
		Integer y = ArgumentHelper.toCoordinate(args[0], 1);
		
		if(y == null) return null;
		
		return new YLevelGoal(y);
	}
	
	public static String[] getUsages() {
		return USAGES;
	}
	
}
