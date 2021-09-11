package de.stylextv.lynx.world.avoidance;

import java.util.HashMap;
import java.util.function.Predicate;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;

public class AvoidanceType<T> {
	
	private static HashMap<Class<?>, AvoidanceType<?>> map = new HashMap<>();
	
	public static final AvoidanceType<ZombieEntity> ZOMBIE = new AvoidanceType<>(ZombieEntity.class, 8, 1.5);
	public static final AvoidanceType<ZombieVillagerEntity> ZOMBIE_VILLAGER = new AvoidanceType<>(ZombieVillagerEntity.class, 8, 1.5);
	public static final AvoidanceType<DrownedEntity> DROWNED = new AvoidanceType<>(DrownedEntity.class, 8, 1.5);
	public static final AvoidanceType<HuskEntity> HUSK = new AvoidanceType<>(HuskEntity.class, 8, 1.5);
	
	public static final AvoidanceType<SkeletonEntity> SKELETON = new AvoidanceType<>(SkeletonEntity.class, 12, 1.5);
	public static final AvoidanceType<StrayEntity> STRAY = new AvoidanceType<>(StrayEntity.class, 12, 1.5);
	public static final AvoidanceType<WitherSkeletonEntity> WITHER_SKELETON = new AvoidanceType<>(WitherSkeletonEntity.class, 10, 1.8);
	
	public static final AvoidanceType<CreeperEntity> CREEPER = new AvoidanceType<>(CreeperEntity.class, 9, 1.5);
	
	public static final AvoidanceType<SpiderEntity> SPIDER = new AvoidanceType<>(SpiderEntity.class, 10, 1.5, (spider) -> spider.getBrightnessAtEyes() < 0.5f);
	public static final AvoidanceType<CaveSpiderEntity> CAVE_SPIDER = new AvoidanceType<>(CaveSpiderEntity.class, 10, 1.6);
	
	public static final AvoidanceType<EndermanEntity> ENDERMAN = new AvoidanceType<>(EndermanEntity.class, 12, 2.0, (enderMan) -> {
		ClientPlayerEntity p = PlayerContext.player();
		
		return enderMan.isAngryAt(p);
	});
	
	public static final AvoidanceType<WitchEntity> WITCH = new AvoidanceType<>(WitchEntity.class, 10, 1.7);
	
	public static final AvoidanceType<SlimeEntity> SLIME = new AvoidanceType<>(SlimeEntity.class, 10, 1.8);
	public static final AvoidanceType<MagmaCubeEntity> MAGMA_CUBE = new AvoidanceType<>(MagmaCubeEntity.class, 12, 1.8);
	
	public static final AvoidanceType<SilverfishEntity> SILVERFISH = new AvoidanceType<>(SilverfishEntity.class, 6, 1.1);
	public static final AvoidanceType<EndermiteEntity> ENDERMITE = new AvoidanceType<>(EndermiteEntity.class, 6, 1.2);
	
	public static final AvoidanceType<GuardianEntity> GUARDIAN = new AvoidanceType<>(GuardianEntity.class, 10, 1.6);
	public static final AvoidanceType<ElderGuardianEntity> ELDER_GUARDIAN = new AvoidanceType<>(ElderGuardianEntity.class, 25, 2.5);
	
	public static final AvoidanceType<BlazeEntity> BLAZE = new AvoidanceType<>(BlazeEntity.class, 12, 1.5);
	public static final AvoidanceType<GhastEntity> GHAST = new AvoidanceType<>(GhastEntity.class, 15, 1.5);
	
	public static final AvoidanceType<ShulkerEntity> SHULKER = new AvoidanceType<>(ShulkerEntity.class, 13, 2.0);
	
	public static final AvoidanceType<VexEntity> VEX = new AvoidanceType<>(VexEntity.class, 6, 1.3);
	public static final AvoidanceType<PhantomEntity> PHANTOM = new AvoidanceType<>(PhantomEntity.class, 10, 1.7);
	
	public static final AvoidanceType<PillagerEntity> PILLAGER = new AvoidanceType<>(PillagerEntity.class, 9, 1.6);
	public static final AvoidanceType<VindicatorEntity> VINDICATOR = new AvoidanceType<>(VindicatorEntity.class, 10, 1.7);
	public static final AvoidanceType<IllusionerEntity> ILLUSIONER = new AvoidanceType<>(IllusionerEntity.class, 10, 1.7);
	public static final AvoidanceType<SpellcastingIllagerEntity> SPELLCASTING_ILLAGER = new AvoidanceType<>(SpellcastingIllagerEntity.class, 11, 1.8);
	public static final AvoidanceType<EvokerEntity> EVOKER = new AvoidanceType<>(EvokerEntity.class, 13, 2.0);
	
	public static final AvoidanceType<PiglinEntity> PIGLIN = new AvoidanceType<>(PiglinEntity.class, 11, 1.7);
	public static final AvoidanceType<PiglinBruteEntity> PIGLIN_BRUTE = new AvoidanceType<>(PiglinBruteEntity.class, 11, 1.7);
	public static final AvoidanceType<ZombifiedPiglinEntity> ZOMBIFIED_PIGLIN = new AvoidanceType<>(ZombifiedPiglinEntity.class, 11, 1.7, (piglin) -> {
		ClientPlayerEntity p = PlayerContext.player();
		
		return piglin.isAngryAt(p);
	});
	
	public static final AvoidanceType<RavagerEntity> RAVAGER = new AvoidanceType<>(RavagerEntity.class, 16, 2.5);
	public static final AvoidanceType<HoglinEntity> HOGLIN = new AvoidanceType<>(HoglinEntity.class, 13, 2.0);
	public static final AvoidanceType<ZoglinEntity> ZOGLIN = new AvoidanceType<>(ZoglinEntity.class, 13, 2.0);
	
	public static final AvoidanceType<EnderDragonEntity> ENDER_DRAGON = new AvoidanceType<>(EnderDragonEntity.class, 20, 3.0);
	public static final AvoidanceType<WitherEntity> WITHER = new AvoidanceType<>(WitherEntity.class, 30, 3.0);
	
	private Class<T> entityClass;
	
	private int radius;
	
	private double coefficient;
	
	private Predicate<T> predicate;
	
	public AvoidanceType(Class<T> entityClass, int radius, double coefficient) {
		this(entityClass, radius, coefficient, null);
	}
	
	public AvoidanceType(Class<T> entityClass, int radius, double coefficient, Predicate<T> predicate) {
		this.entityClass = entityClass;
		this.radius = radius;
		this.coefficient = coefficient;
		this.predicate = predicate;
		
		registerType(this);
	}
	
	public boolean shouldIgnore(Entity e) {
		T t = entityClass.cast(e);
		
		if(predicate == null) return false;
		
		return !predicate.test(t);
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
		map.put(type.getEntityClass(), type);
	}
	
	public static AvoidanceType<?> fromEntity(Entity e) {
		Class<? extends Entity> clazz = e.getClass();
		
		AvoidanceType<?> type = map.get(clazz);
		
		if(type == null || type.shouldIgnore(e)) return null;
		
		return type;
	}
	
}
