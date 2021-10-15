package de.stylextv.maple.world.scan.entity;

import java.util.function.Predicate;

import de.stylextv.maple.util.RegistryUtil;
import de.stylextv.maple.world.scan.ScanFilter;
import de.stylextv.maple.world.scan.entity.filters.EntityNameFilter;
import de.stylextv.maple.world.scan.entity.filters.EntityTypeFilter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

public class EntityFilter extends ScanFilter<Entity> {
	
	public static final EntityFilter ALL = new EntityFilter((e) -> {
		return true;
	});
	
	public static final EntityFilter ALIVE = new EntityFilter((e) -> {
		return e instanceof LivingEntity && e.isAlive();
	});
	
	public EntityFilter() {}
	
	public EntityFilter(Predicate<Entity> predicate) {
		super(predicate);
	}
	
	public static EntityFilter fromString(String s) {
		EntityType<?> type = RegistryUtil.getEntityType(s);
		
		if(type == null) return new EntityNameFilter(s);
		
		return new EntityTypeFilter(type);
	}
	
}
