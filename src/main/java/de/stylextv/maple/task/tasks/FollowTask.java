package de.stylextv.maple.task.tasks;

import java.util.List;

import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.entity.EntityFilter;
import de.stylextv.maple.world.scan.entity.EntityScanner;
import net.minecraft.entity.Entity;

public class FollowTask extends CompositeTask {
	
	private static final float FOLLOW_DISTANCE = 3f;
	
	private EntityFilter filter;
	
	public FollowTask(EntityFilter filter) {
		this.filter = filter;
	}
	
	@Override
	public CompositeGoal onTick() {
		List<Entity> entities = EntityScanner.scanWorld(filter, EntityFilter.ALIVE);
		
		return CompositeGoal.fromEntities(entities, FOLLOW_DISTANCE);
	}
	
	@Override
	public void onFail() {
		ChatUtil.send("Can't get any closer to entity.");
	}
	
	@Override
	public void onComplete() {
		ChatUtil.send("Can't find any matching entities nearby.");
	}
	
	public EntityFilter getFilter() {
		return filter;
	}
	
}
