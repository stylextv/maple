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
		if(segment != null) segments.remove(0);
		
		if(segments.isEmpty()) {
			
			segment = null;
			
			return;
		}
		
		segment = segments.get(0);
	}
	
	public BlockPos lastPosition() {
		if(segments.isEmpty()) return PlayerContext.feetPosition();
		
		int l = segments.size();
		
		PathSegment s = segments.get(l - 1);
		
		return s.lastPosition();
	}
	
	public boolean needsNewSegment() {
		int l = nodesLeft();
		
		return l < MIN_NODE_AMOUNT;
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
		return getSegment() == null;
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
