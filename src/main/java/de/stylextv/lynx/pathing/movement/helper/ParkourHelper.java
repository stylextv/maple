package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class ParkourHelper extends MovementHelper {
	
	public ParkourHelper(Movement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		Movement m = getMovement();
		
		int dis = m.horizontalDistance();
		
		if(dis > 5) return Cost.INFINITY;
		
		Node source = m.getSource();
		
		int startX = source.getX();
		int startY = source.getY();
		int startZ = source.getZ();
		
		int dx = m.getDirectionX();
		int dz = m.getDirectionZ();
		
		for(int i = 0; i <= dis; i++) {
			
			int x = startX + dx * i;
			int y = startY;
			int z = startZ + dz * i;
			
			if(BumpHelper.isBlocked(x, y, z, 3)) return Cost.INFINITY;
			
			if(i != 0 && i != dis) {
				
				if(BumpHelper.isBlocked(x, y, z, -1, 1)) return Cost.INFINITY;
			}
		}
		
		return dis * Cost.SPRINT_STRAIGHT;
	}
	
}
