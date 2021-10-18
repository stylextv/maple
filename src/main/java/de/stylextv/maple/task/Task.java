package de.stylextv.maple.task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.stylextv.maple.pathing.PathingCommand;
import de.stylextv.maple.pathing.PathingExecutor;
import de.stylextv.maple.pathing.PathingStatus;

public abstract class Task {
	
	private Task parent;
	
	private List<Task> children = new CopyOnWriteArrayList<>();
	
	private boolean paused = true;
	
	private boolean finished;
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	
	public void appendTask(Task t) {
		children.add(t);
		
		t.setParent(this);
	}
	
	public PathingCommand onRenderTick(PathingStatus status) {
		if(!finished) {
			
			finished = true;
			
			PathingExecutor.stopPathing();
		}
		
		for(Task t : children) {
			
			PathingCommand c = t.onRenderTick(status);
			
			if(c != null) return c;
			
			children.remove(t);
		}
		
		return null;
	}
	
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	public Task getParent() {
		return parent;
	}
	
	public void setParent(Task parent) {
		this.parent = parent;
	}
	
	public List<Task> getChildren() {
		return children;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
}
