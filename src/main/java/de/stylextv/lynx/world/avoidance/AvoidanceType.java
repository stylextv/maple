package de.stylextv.lynx.world.avoidance;

import java.util.HashMap;
import java.util.function.Predicate;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;

public class AvoidanceType<T> {
	
	private static HashMap<Class<?>, AvoidanceType<?>> map = new HashMap<>();
	
	public static final AvoidanceType<Zombie> ZOMBIE = new AvoidanceType<>(Zombie.class, 8, 1.5);
	public static final AvoidanceType<ZombieVillager> ZOMBIE_VILLAGER = new AvoidanceType<>(ZombieVillager.class, 8, 1.5);
	public static final AvoidanceType<Drowned> DROWNED = new AvoidanceType<>(Drowned.class, 8, 1.5);
	public static final AvoidanceType<Husk> HUSK = new AvoidanceType<>(Husk.class, 8, 1.5);
	
	public static final AvoidanceType<Skeleton> SKELETON = new AvoidanceType<>(Skeleton.class, 12, 1.5);
	public static final AvoidanceType<Stray> STRAY = new AvoidanceType<>(Stray.class, 12, 1.5);
	public static final AvoidanceType<WitherSkeleton> WITHER_SKELETON = new AvoidanceType<>(WitherSkeleton.class, 10, 1.8);
	
	public static final AvoidanceType<Creeper> CREEPER = new AvoidanceType<>(Creeper.class, 9, 1.5);
	
	public static final AvoidanceType<Spider> SPIDER = new AvoidanceType<>(Spider.class, 10, 1.5, (spider) -> spider.getBrightness() < 0.5f);
	public static final AvoidanceType<CaveSpider> CAVE_SPIDER = new AvoidanceType<>(CaveSpider.class, 10, 1.6);
	
	public static final AvoidanceType<EnderMan> ENDER_MAN = new AvoidanceType<>(EnderMan.class, 12, 2.0, (enderMan) -> {
		LocalPlayer p = PlayerContext.player();
		
		return enderMan.isAngryAt(p);
	});
	
	public static final AvoidanceType<Witch> WITCH = new AvoidanceType<>(Witch.class, 10, 1.7);
	
	public static final AvoidanceType<Slime> SLIME = new AvoidanceType<>(Slime.class, 10, 1.8);
	public static final AvoidanceType<MagmaCube> MAGMA_CUBE = new AvoidanceType<>(MagmaCube.class, 12, 1.8);
	
	public static final AvoidanceType<Silverfish> SILVERFISH = new AvoidanceType<>(Silverfish.class, 6, 1.1);
	public static final AvoidanceType<Endermite> ENDERMITE = new AvoidanceType<>(Endermite.class, 6, 1.2);
	
	public static final AvoidanceType<Guardian> GUARDIAN = new AvoidanceType<>(Guardian.class, 10, 1.6);
	public static final AvoidanceType<ElderGuardian> ELDER_GUARDIAN = new AvoidanceType<>(ElderGuardian.class, 25, 2.5);
	
	public static final AvoidanceType<Blaze> BLAZE = new AvoidanceType<>(Blaze.class, 12, 1.5);
	public static final AvoidanceType<Ghast> GHAST = new AvoidanceType<>(Ghast.class, 15, 1.5);
	
	public static final AvoidanceType<Shulker> SHULKER = new AvoidanceType<>(Shulker.class, 13, 2.0);
	
	public static final AvoidanceType<Vex> VEX = new AvoidanceType<>(Vex.class, 6, 1.3);
	public static final AvoidanceType<Phantom> PHANTOM = new AvoidanceType<>(Phantom.class, 10, 1.7);
	
	public static final AvoidanceType<Pillager> PILLAGER = new AvoidanceType<>(Pillager.class, 9, 1.6);
	public static final AvoidanceType<Vindicator> VINDICATOR = new AvoidanceType<>(Vindicator.class, 10, 1.7);
	public static final AvoidanceType<Illusioner> ILLUSIONER = new AvoidanceType<>(Illusioner.class, 10, 1.7);
	public static final AvoidanceType<SpellcasterIllager> SPELLCASTER_ILLAGER = new AvoidanceType<>(SpellcasterIllager.class, 11, 1.8);
	public static final AvoidanceType<Evoker> EVOKER = new AvoidanceType<>(Evoker.class, 13, 2.0);
	
	public static final AvoidanceType<Piglin> PIGLIN = new AvoidanceType<>(Piglin.class, 11, 1.7);
	public static final AvoidanceType<PiglinBrute> PIGLIN_BRUTE = new AvoidanceType<>(PiglinBrute.class, 11, 1.7);
	public static final AvoidanceType<ZombifiedPiglin> ZOMBIFIED_PIGLIN = new AvoidanceType<>(ZombifiedPiglin.class, 11, 1.7, (piglin) -> {
		LocalPlayer p = PlayerContext.player();
		
		return piglin.isAngryAt(p);
	});
	
	public static final AvoidanceType<Ravager> RAVAGER = new AvoidanceType<>(Ravager.class, 16, 2.5);
	public static final AvoidanceType<Hoglin> HOGLIN = new AvoidanceType<>(Hoglin.class, 13, 2.0);
	public static final AvoidanceType<Zoglin> ZOGLIN = new AvoidanceType<>(Zoglin.class, 13, 2.0);
	
	public static final AvoidanceType<EnderDragon> ENDER_DRAGON = new AvoidanceType<>(EnderDragon.class, 20, 3.0);
	public static final AvoidanceType<WitherBoss> WITHER = new AvoidanceType<>(WitherBoss.class, 30, 3.0);
	
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
