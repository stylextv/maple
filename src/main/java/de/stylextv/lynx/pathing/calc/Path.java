package de.stylextv.lynx.pathing.calc;

import java.util.ArrayList;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.util.TimeUtil;
import net.minecraft.core.BlockPos;

public class Path {
	
	private ArrayList<PathSegment> segments = new ArrayList<>();
	
	private PathSegment segment;
	
	private boolean finished;
	
	public void add(PathSegment s) {
		segments.add(s);
	}
	
	public void next() {
		PathSegment s = getSegment();
		
		s.next();
	}
	
	private void nextSegment() {
		if(segment != null) segments.remove(0);
		
		if(isEmpty()) {
			
			segment = null;
			
			return;
		}
		
		segment = segments.get(0);
	}
	
	// TODO if path is empty give estimate based on goal
	public long timeToGoal() {
		if(isEmpty()) return 0;
		
		long time = timeLeft();
		
		Node n = lastNode();
		
		time += TimeUtil.ticksToMS(n.getHCost());
		
		return time;
	}
	
	public long timeLeft() {
		double ticks = ticksLeft();
		
		return TimeUtil.ticksToMS(ticks);
	}
	
	public double ticksLeft() {
		double sum = 0;
		
		for(PathSegment s : segments) {
			
			sum += s.ticksLeft();
		}
		
		return sum;
	}
	
	public int nodesLeft() {
		int sum = 0;
		
		for(PathSegment s : segments) {
			
			sum += s.nodesLeft();
		}
		
		return sum;
	}
	
	public BlockPos lastPosition() {
		if(isEmpty()) return PlayerContext.feetPosition();
		
		return lastNode().blockPos();
	}
	
	public Node lastNode() {
		int l = segments.size();
		
		PathSegment s = segments.get(l - 1);
		
		return s.lastNode();
	}
	
	public Movement getCurrentMovement() {
		PathSegment s = getSegment();
		
		if(s == null) return null;
		
		return s.getCurrentMovement();
	}
	
	public PathSegment getSegment() {
		if(segment == null || segment.isFinished()) nextSegment();
		
		return segment;
	}
	
	public boolean isEmpty() {
		return segments.isEmpty();
	}
	
	public ArrayList<PathSegment> getAllSegments() {
		return segments;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
}
