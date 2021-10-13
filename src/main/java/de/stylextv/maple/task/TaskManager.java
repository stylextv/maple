package de.stylextv.maple.task;

import de.stylextv.maple.pathing.PathingCommand;
import de.stylextv.maple.pathing.PathingExecutor;
import de.stylextv.maple.pathing.PathingStatus;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.task.tasks.CustomGoalTask;

public class TaskManager {
	
	private static Task task;
	
	public static void gotoGoal(Goal goal) {
		Task task = new CustomGoalTask(goal);
		
		startTask(task);
	}
	
	public static void startTask(Task t) {
		if(hasTask()) stopTask();
		
		task = t;
		
		resumeTask();
	}
	
	public static void stopTask() {
		pauseTask();
		
		task = null;
	}
	
	public static void pauseTask() {
		task.pause();
	}
	
	public static void resumeTask() {
		task.resume();
	}
	
	public static void onTick() {
		PathingCommand c = null;
		
		if(hasTask() && !task.isPaused()) {
			
			PathingStatus status = PathingExecutor.getStatus();
			
			c = task.onTick(status);
			
			if(c == null) stopTask();
		}
		
		PathingExecutor.processCommand(c);
	}
	
	public static boolean hasTask() {
		return task != null;
	}
	
	public static Task getTask() {
		return task;
	}
	
}
