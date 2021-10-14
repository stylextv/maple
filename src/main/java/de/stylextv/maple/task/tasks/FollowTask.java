package de.stylextv.maple.task.tasks;

import java.util.List;

import de.stylextv.maple.pathing.PathingCommand;
import de.stylextv.maple.pathing.PathingCommandType;
import de.stylextv.maple.pathing.PathingStatus;
import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.task.Task;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.entity.EntityFilter;
import de.stylextv.maple.world.scan.entity.EntityScanner;
import net.minecraft.entity.Entity;

public class FollowTask extends Task {
	
	private static final float FOLLOW_DISTANCE = 3f;
	
	private EntityFilter filter;
	
	public FollowTask(EntityFilter filter) {
		this.filter = filter;
	}
	
	@Override
	public PathingCommand onTick(PathingStatus status) {
		List<Entity> entities = EntityScanner.scanWorld(filter, EntityFilter.ALIVE);
		
		CompositeGoal goal = CompositeGoal.fromEntities(entities, FOLLOW_DISTANCE);
		
		boolean empty = goal.isEmpty();
		
		if(status.isPathing()) {
			
			if(empty) return PathingCommand.DEFER;
			
			return new PathingCommand(PathingCommandType.REVALIDATE_GOAL, goal);
		}
		
		if(status.goalMatches(goal) && !status.isAtGoal()) {
			
			ChatUtil.send("Can't get any closer to entity.");
			
			return super.onTick(status);
		}
		
		if(!empty) return new PathingCommand(PathingCommandType.PATH_TO_GOAL, goal);
		
		ChatUtil.send("Can't find any matching entities nearby.");
		
		return super.onTick(status);
	}
	
	public EntityFilter getFilter() {
		return filter;
	}
	
}
