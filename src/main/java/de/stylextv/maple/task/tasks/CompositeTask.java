package de.stylextv.maple.task.tasks;

import de.stylextv.maple.pathing.PathingCommand;
import de.stylextv.maple.pathing.PathingCommandType;
import de.stylextv.maple.pathing.PathingStatus;
import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.task.Task;

public abstract class CompositeTask extends Task {
	
	public abstract CompositeGoal onTick();
	
	public abstract void onFail();
	
	public abstract void onComplete();
	
	@Override
	public PathingCommand onTick(PathingStatus status) {
		CompositeGoal goal = onTick();
		
		if(goal == null) return PathingCommand.PAUSE;
		
		boolean empty = goal.isEmpty();
		
		if(status.isPathing()) {
			
			if(empty) return PathingCommand.CANCEL;
			
			return new PathingCommand(PathingCommandType.REVALIDATE_GOAL, goal);
		}
		
		if(status.goalMatches(goal) && !status.isAtGoal()) {
			
			onFail();
			
			return super.onTick(status);
		}
		
		if(!empty) return new PathingCommand(PathingCommandType.PATH_TO_GOAL, goal);
		
		onComplete();
		
		return super.onTick(status);
	}
	
}
