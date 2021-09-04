package de.stylextv.lynx.world.avoidance;

import de.stylextv.lynx.pathing.calc.Cost;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;

public class AvoidanceType<T> {
	
	private static final AvoidanceType<?>[] TYPES = new AvoidanceType[1];
	
	public static final AvoidanceType<Zombie> ZOMBIE = new AvoidanceType<>(Zombie.class, 6, Cost.INFINITY);
	
	private static int pointer;
	
	private Class<T> entityClass;
	
	private int radius;
	
	private double coefficient;
	
	public AvoidanceType(Class<T> entityClass, int radius, double coefficient) {
		this.entityClass = entityClass;
		this.radius = radius;
		this.coefficient = coefficient;
		
		registerType(this);
	}
	
	public boolean matches(Object o) {
		if(!entityClass.isInstance(o)) return false;
		
		T e = entityClass.cast(o);
		
		return !shouldIgnore(e);
	}
	
	public boolean shouldIgnore(T e) {
		return false;
	}
	
	public T castEntity(Object o) {
		return entityClass.cast(o);
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public double getCoefficient() {
		return coefficient;
	}
	
	private static void registerType(AvoidanceType<?> type) {
		TYPES[pointer] = type;
		
		pointer++;
	}
	
	public static AvoidanceType<?> fromEntity(Entity e) {
		for(AvoidanceType<?> type : TYPES) {
			
			if(type.matches(e)) return type;
		}
		
		return null;
	}
	
	public static AvoidanceType<?>[] getTypes() {
		return TYPES;
	}
	
}
