package de.stylextv.lynx.world.avoidance;

import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.util.world.CoordUtil;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

public class Avoidance {
	
	private static final long UPDATE_DELAY = 5000;
	
	private static Long2DoubleOpenHashMap map = new Long2DoubleOpenHashMap(512);
	
	private static long lastUpdate;
	
	static {
		map.defaultReturnValue(1);
	}
	
	private BlockPos pos;
	
	private AvoidanceType<?> type;
	
	public Avoidance(BlockPos pos, AvoidanceType<?> type) {
		this.pos = pos;
		this.type = type;
	}
	
	public void apply() {
		int r = type.getRadius();
		
		for(int x = -r; x <= r; x++) {
			for(int y = -r; y <= r; y++) {
				for(int z = -r; z <= r; z++) {
					
					int dis = x * x + y * y + z * z;
					
					if(dis <= r * r) {
						
						int rx = pos.getX() + x;
						int ry = pos.getY() + y;
						int rz = pos.getZ() + z;
						
						long hash = CoordUtil.posAsLong(rx, ry, rz);
						
						double cost = type.getCost();
						
						cost *= map.get(hash);
						
						map.put(hash, cost);
					}
				}
			}
		}
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public AvoidanceType<?> getType() {
		return type;
	}
	
	public static void update() {
		lastUpdate = System.currentTimeMillis();
		
		map.clear();
		
		ClientLevel level = LevelContext.level();
		
		for(Entity e : level.entitiesForRendering()) {
			
			AvoidanceType<?> type = AvoidanceType.fromEntity(e);
			
			if(type == null) continue;
			
			BlockPos pos = e.blockPosition();
			
			Avoidance a = new Avoidance(pos, type);
			
			a.apply();
		}
	}
	
	public static double getCost(int x, int y, int z) {
		long dt = System.currentTimeMillis() - lastUpdate;
		
		if(dt > UPDATE_DELAY) update();
		
		long hash = CoordUtil.posAsLong(x, y, z);
		
		return map.get(hash);
	}
	
}
