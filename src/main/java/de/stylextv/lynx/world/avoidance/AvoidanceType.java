package de.stylextv.lynx.world.avoidance;

import de.stylextv.lynx.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;

public class AvoidanceType {
	
	public static final AvoidanceType WEAK = new AvoidanceType(6, 1.2);
	
	public static final AvoidanceType NORMAL = new AvoidanceType(8, 1.5);
	
	public static final AvoidanceType STRONG = new AvoidanceType(10, 1.9);
	
	public static final AvoidanceType BEAST = new AvoidanceType(12, 2.5);
	
	private static final AvoidanceType[] TYPES = new AvoidanceType[] {
			WEAK, NORMAL, STRONG, BEAST
	};
	
	private int radius;
	
	private double coefficient;
	
	public AvoidanceType(int radius, double coefficient) {
		this.radius = radius;
		this.coefficient = coefficient;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public double getCoefficient() {
		return coefficient;
	}
	
	public static AvoidanceType fromEntity(Entity e) {
		if(e instanceof Monster) {
			
			if(AvoidanceFilter.shouldIgnore(e)) return null;
			
			LivingEntity entity = (LivingEntity) e;
			
			float health = entity.getMaxHealth();
			
			double log = MathUtil.log2(health);
			
			int i = (int) log - 3;
			
			int max = TYPES.length - 1;
			
			i = MathUtil.clamp(i, 0, max);
			
			return TYPES[i];
		}
		
		return null;
	}
	
}
