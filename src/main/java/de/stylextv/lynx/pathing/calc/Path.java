package de.stylextv.lynx.pathing.calc;

import java.util.ArrayList;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.pathing.movement.Movement;
import net.minecraft.core.BlockPos;

public class Path {
	
	private static final int MIN_NODE_AMOUNT = 30;
	
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
		if(segments.isEmpty()) {
			
			segment = null;
			
			return;
		}
		
		segment = segments.remove(0);
	}
	
	public BlockPos lastPosition() {
		if(!segments.isEmpty()) {
			
			int l = segments.size();
			
			PathSegment s = segments.get(l - 1);
			
			return s.lastPosition();
		}
		
		if(segment != null) return segment.lastPosition();
		
		return PlayerContext.feetPosition();
	}
	
	public boolean needsNewSegment() {
		int l = nodesLeft();
		
		return l < MIN_NODE_AMOUNT;
	}
	
	public int nodesLeft() {
		int sum = 0;
		
		PathSegment segment = getSegment();
		
		if(segment != null) sum += segment.nodesLeft();
		
		for(PathSegment s : segments) {
			
			sum += s.nodesLeft();
		}
		
		return sum;
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
	
	public ArrayList<PathSegment> getQueuedSegments() {
		return segments;
	}
	
	public boolean isEmpty() {
		return getSegment() == null;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
}
