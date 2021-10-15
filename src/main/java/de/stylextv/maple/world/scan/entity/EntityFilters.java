package de.stylextv.maple.world.scan.entity;

import de.stylextv.maple.world.scan.ScanFilters;
import net.minecraft.entity.Entity;

public class EntityFilters extends ScanFilters<EntityFilter, Entity> {
	
	public EntityFilters(EntityFilter... filters) {
		super(filters);
	}
	
	public static EntityFilters fromFilter(EntityFilter filter) {
		return new EntityFilters(filter);
	}
	
	public static EntityFilters fromFilters(EntityFilter... filters) {
		return new EntityFilters(filters);
	}
	
}
