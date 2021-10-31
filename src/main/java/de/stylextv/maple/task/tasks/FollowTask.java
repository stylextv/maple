package de.stylextv.maple.task.tasks;

import java.util.List;

import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.pathing.calc.goal.NearGoal;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.entity.EntityFilter;
import de.stylextv.maple.world.scan.entity.EntityFilters;
import de.stylextv.maple.world.scan.entity.EntityScanner;
import net.minecraft.entity.Entity;

public class FollowTask extends CompositeTask {
	
	private static final float FOLLOW_DISTANCE = 3f;
	
	private EntityFilters filters;
	
	public FollowTask(EntityFilter filter) {
		super(false);
		
		this.filters = EntityFilters.fromFilters(filter, EntityFilter.ALIVE);
	}
	
	@Override
	public CompositeGoal onRenderTick() {
		List<Entity> entities = EntityScanner.scanWorld(filters);
		
		return CompositeGoal.fromCollection(entities, e -> new NearGoal(e.getBlockPos(), FOLLOW_DISTANCE));
	}
	
	@Override
	public void onFail() {
		ChatUtil.send("Can't get any closer to entities.");
	}
	
	@Override
	public void onEmptyGoal() {
		ChatUtil.send("Can't find any matching entities nearby.");
	}
	
	public EntityFilters getFilters() {
		return filters;
	}
	
}
