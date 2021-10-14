package de.stylextv.maple.world.scan.entity.filters;

import de.stylextv.maple.world.scan.entity.EntityFilter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

public class LivingEntityFilter extends EntityFilter {
	
	private EntityType<?> type;
	
	public LivingEntityFilter(EntityType<?> type) {
		this.type = type;
	}
	
	@Override
	public boolean matches(Entity e) {
		return e instanceof LivingEntity && e.isAlive();
	}
	
	public EntityType<?> getType() {
		return type;
	}
	
}
