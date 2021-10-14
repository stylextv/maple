package de.stylextv.maple.world.scan.entity.filters;

import de.stylextv.maple.world.scan.entity.EntityFilter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class EntityTypeFilter extends EntityFilter {
	
	private EntityType<?> type;
	
	public EntityTypeFilter(EntityType<?> type) {
		this.type = type;
	}
	
	@Override
	public boolean matches(Entity e) {
		EntityType<?> t = e.getType();
		
		return t.equals(type);
	}
	
	public EntityType<?> getType() {
		return type;
	}
	
}
