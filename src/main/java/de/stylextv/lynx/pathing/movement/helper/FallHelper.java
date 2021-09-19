package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.movements.FallMovement;

public class FallHelper extends MovementHelper<FallMovement> {
	
	public FallHelper(FallMovement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		FallMovement m = getMovement();
		
		int fallDistance = m.getFallDistance();
		
		if(fallDistance > 3) {
			
//			ToolSet tools = ToolSet.getTools();
//			
//			if(tools.hasWaterBucket()) return 0;
			
			Node destination = m.getDestination();
			
			BlockType type = destination.getType();
			
			if(type != BlockType.WATER) return Cost.INFINITY;
		}
		
		return 0;
	}
	
}
