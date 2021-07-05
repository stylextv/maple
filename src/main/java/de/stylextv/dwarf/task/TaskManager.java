package de.stylextv.dwarf.task;

public class TaskManager {
	
	private static Cycle currentCycle;
	
	private static Task currentTask;
	
	public static void startCycle(Cycle c, String[] args) {
		currentCycle = c;
		currentCycle.start(args);
	}
	
	public static void startTask(Task t) {
		stopCurrentTask();
		
		currentTask = t;
		
		currentTask.start();
	}
	
	public static void stopCurrentCycle() {
		currentCycle = null;
		
		stopCurrentTask();
	}
	
	public static void stopCurrentTask() {
		if(currentTask != null) {
			currentTask.stop();
			
			currentTask = null;
		}
	}
	
	public static void onTick() {
		if(currentTask != null && currentTask.run()) {
			stopCurrentTask();
		}
		
		if(currentTask == null && currentCycle != null && currentCycle.run()) {
			stopCurrentCycle();
		}
	}
	
	public static Cycle getCurrentCycle() {
		return currentCycle;
	}
	
}
