package de.stylextv.maple.world.scan.entity;

import de.stylextv.maple.command.ArgumentHelper;
import de.stylextv.maple.world.scan.entity.filters.EntityNameFilter;
import de.stylextv.maple.world.scan.entity.filters.EntityTypeFilter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public abstract class EntityFilter {
	
	public static final EntityFilter ALL = new EntityFilter() {
		
		@Override
		public boolean matches(Entity e) {
			return true;
		}
	};
	
	public abstract boolean matches(Entity e);
	
	public static EntityFilter fromString(String s) {
		EntityType<?> type = ArgumentHelper.toEntityType(s);
		
		if(type == null) return new EntityNameFilter(s);
		
		return new EntityTypeFilter(type);
	}
	
}
