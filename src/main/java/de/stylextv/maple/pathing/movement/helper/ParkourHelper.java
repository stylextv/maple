package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.movements.ParkourMovement;

public class ParkourHelper extends MovementHelper<ParkourMovement> {
	
	public ParkourHelper(ParkourMovement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		ParkourMovement m = getMovement();
		
		int dis = m.getDistance();
		
		Node source = m.getSource();
		
		int startX = source.getX();
		int startY = source.getY();
		int startZ = source.getZ();
		
		int dx = m.getDeltaX();
		int dz = m.getDeltaZ();
		
		for(int i = 0; i <= dis; i++) {
			
			int x = startX + dx * i;
			int y = startY;
			int z = startZ + dz * i;
			
			if(isObstructed(x, y, z, 3)) return Cost.INFINITY;
		}
		
		boolean sprint = shouldSprint();
		
		double cost = sprint ? Cost.SPRINT_STRAIGHT : Cost.WALK_STRAIGHT;
		
		return cost * dis;
	}
	
	public boolean shouldSprint() {
		ParkourMovement m = getMovement();
		
		double dis = m.getDistance();
		
		return dis > 3;
	}
	
	public static boolean isObstructed(Node n, int height) {
		return isObstructed(n, 0, height);
	}
	
	public static boolean isObstructed(Node n, int offset, int height) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return isObstructed(x, y, z, offset, height);
	}
	
	public static boolean isObstructed(int x, int y, int z, int height) {
		return isObstructed(x, y, z, 0, height);
	}
	
	public static boolean isObstructed(int x, int y, int z, int offset, int height) {
		for(int i = 0; i < height; i++) {
			
			if(isObstructed(x, y + offset + i, z)) return true;
		}
		
		return false;
	}
	
	public static boolean isObstructed(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(!type.isAir()) return true;
		
		float f = StepHelper.getSpeedMultiplier(x, y, z);
		
		return f != 1;
	}
	
}
