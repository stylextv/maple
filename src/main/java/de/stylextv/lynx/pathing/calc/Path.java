package de.stylextv.lynx.pathing.calc;

import java.util.ArrayList;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.util.time.TimeUtil;
import net.minecraft.core.BlockPos;

public class Path {
	
	private static final int OBSTACLE_LOOKAHEAD = 5;
	
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
	
	public void clear() {
		segments.clear();
		
		segment = null;
		
		finished = false;
	}
	
	// TODO if path is empty give estimate based on goal
	public long timeToGoal() {
		if(isEmpty()) return 0;
		
		long time = timeLeft();
		
		if(!isFinished()) {
			
			Node n = lastNode();
			
			time += TimeUtil.ticksToMS(n.getHCost());
		}
		
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
	
	public double distanceSqr(BlockPos pos) {
		Movement m = getCurrentMovement();
		
		if(m == null) return 0;
		
		return m.distanceSqr(pos);
	}
	
	public Movement getCurrentMovement() {
		PathSegment s = getSegment();
		
		if(s == null) return null;
		
		return s.getCurrentMovement();
	}
	
	public Movement getNextMovement(int offset) {
		for(PathSegment s : segments) {
			
			int k = s.getPointer();
			
			int l = s.length() - k;
			
			if(offset >= l) {
				offset -= l;
				
				continue;
			}
			
			return s.getMovement(offset + k);
		}
		
		return null;
	}
	
	public PathSegment getSegment() {
		if(segment == null || segment.isFinished()) nextSegment();
		
		return segment;
	}
	
	public boolean isImpossible() {
		for(int i = 0; i < OBSTACLE_LOOKAHEAD; i++) {
			
			Movement m = getNextMovement(i);
			
			if(m == null) break;
			
			if(m.isImpossible()) return true;
		}
		
		return false;
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
