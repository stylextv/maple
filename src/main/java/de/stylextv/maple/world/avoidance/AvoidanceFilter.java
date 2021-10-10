package de.stylextv.maple.world.avoidance;

import java.util.HashMap;
import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.SpiderEntity;

public class AvoidanceFilter<T> {
	
	private static HashMap<Class<?>, AvoidanceFilter<?>> map = new HashMap<>();
	
	public static final AvoidanceFilter<SpiderEntity> SPIDER = new AvoidanceFilter<>(SpiderEntity.class, (spider) -> {
		return spider.getBrightnessAtEyes() < 0.5f;
	});
	
	public static final AvoidanceFilter<SlimeEntity> SLIME = new AvoidanceFilter<>(SlimeEntity.class, (slime) -> {
		return slime.getSize() > 1;
	});
	
	public static final AvoidanceFilter<MagmaCubeEntity> MAGMA_CUBE = new AvoidanceFilter<>(MagmaCubeEntity.class, (magmaCube) -> {
		return magmaCube.getSize() > 1;
	});
	
	private Class<T> entityClass;
	
	private Predicate<T> predicate;
	
	public AvoidanceFilter(Class<T> entityClass, Predicate<T> predicate) {
		this.entityClass = entityClass;
		this.predicate = predicate;
		
		registerFilter(this);
	}
	
	public boolean ignores(Entity e) {
		if(predicate == null) return false;
		
		T t = entityClass.cast(e);
		
		return !predicate.test(t);
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	private static void registerFilter(AvoidanceFilter<?> filter) {
		map.put(filter.getEntityClass(), filter);
	}
	
	public static boolean shouldIgnore(Entity e) {
		Class<? extends Entity> clazz = e.getClass();
		
		AvoidanceFilter<?> filter = map.get(clazz);
		
		if(filter == null) return false;
		
		return filter.ignores(e);
	}
	
}
