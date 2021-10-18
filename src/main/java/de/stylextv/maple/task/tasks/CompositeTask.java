package de.stylextv.maple.task.tasks;

import de.stylextv.maple.pathing.PathingCommand;
import de.stylextv.maple.pathing.PathingCommandType;
import de.stylextv.maple.pathing.PathingStatus;
import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.task.Task;

public abstract class CompositeTask extends Task {
	
	public abstract CompositeGoal onRenderTick();
	
	public abstract void onFail();
	
	public abstract void onComplete();
	
	@Override
	public PathingCommand onRenderTick(PathingStatus status) {
		CompositeGoal goal = onRenderTick();
		
		if(goal == null) return PathingCommand.PAUSE;
		
		boolean empty = goal.isEmpty();
		
		if(status.isPathing()) {
			
			if(empty) return PathingCommand.CANCEL;
			
			return new PathingCommand(PathingCommandType.REVALIDATE_GOAL, goal);
		}
		
		if(status.goalMatches(goal) && !status.isAtGoal()) {
			
			onFail();
			
			return super.onRenderTick(status);
		}
		
		if(!empty) return new PathingCommand(PathingCommandType.PATH_TO_GOAL, goal);
		
		onComplete();
		
		return super.onRenderTick(status);
	}
	
}
