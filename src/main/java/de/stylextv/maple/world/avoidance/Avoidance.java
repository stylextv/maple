package de.stylextv.maple.world.avoidance;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.util.world.CoordUtil;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class Avoidance {
	
	private BlockPos pos;
	
	private AvoidanceType type;
	
	public Avoidance(BlockPos pos, AvoidanceType type) {
		this.pos = pos;
		this.type = type;
	}
	
	public void apply(Long2DoubleOpenHashMap map) {
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
						
						double d = type.getCoefficient();
						
						d *= map.get(hash);
						
						map.put(hash, d);
					}
				}
			}
		}
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public AvoidanceType getType() {
		return type;
	}
	
	public static List<Avoidance> list() {
		List<Avoidance> list = new ArrayList<>();
		
		ClientWorld world = WorldContext.world();
		
		for(Entity e : world.getEntities()) {
			
			AvoidanceType type = AvoidanceType.fromEntity(e);
			
			if(type == null) continue;
			
			BlockPos pos = e.getBlockPos();
			
			Avoidance a = new Avoidance(pos, type);
			
			list.add(a);
		}
		
		return list;
	}
	
}
