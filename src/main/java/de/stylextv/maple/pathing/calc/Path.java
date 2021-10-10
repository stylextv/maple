package de.stylextv.maple.pathing.calc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.util.time.TimeUtil;
import net.minecraft.util.math.BlockPos;

public class Path {
	
	private static final int OBSTACLE_LOOKAHEAD = 6;
	
	private List<PathSegment> segments = new CopyOnWriteArrayList<>();
	
	private PathSegment segment;
	
	private PathState state = PathState.PAUSED;
	
	public void add(PathSegment s) {
		Node n = s.findMatch(this);
		
		if(n != null) {
			
			s.trimToNode(n);
			
			trimFromNode(n);
		}
		
		if(!s.isEmpty()) segments.add(s);
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
		
		state = PathState.PAUSED;
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
		PathSegment s = lastSegment();
		
		return s.lastNode();
	}
	
	public PathSegment lastSegment() {
		int l = length();
		
		if(l == 0) return null;
		
		PathSegment s = segments.get(l - 1);
		
		return s;
	}
	
	public double distanceSqr(BlockPos pos) {
		Movement m = getCurrentMovement();
		
		if(m == null) return 0;
		
		return m.squaredDistanceTo(pos);
	}
	
	public void trimFromNode(Node n) {
		int i = indexOf(n);
		
		int amount = lengthInNodes() - 1 - i;
		
		trim(amount);
	}
	
	public void trim(int amount) {
		while(!isEmpty() && amount > 0) {
			
			int i = length() - 1;
			
			PathSegment s = segments.get(i);
			
			amount -= s.trim(amount);
			
			if(!s.isEmpty()) return;
			
			segments.remove(i);
		}
	}
	
	public int indexOf(Node n) {
		int i = 0;
		
		for(PathSegment s : segments) {
			
			int index = s.indexOf(n);
			
			if(index != -1) return i + index;
			
			i += s.length();
		}
		
		return -1;
	}
	
	public int getCurrentIndex() {
		Node n = getCurrentNode();
		
		if(n == null) return -1;
		
		return indexOf(n);
	}
	
	public Node getCurrentNode() {
		PathSegment s = getSegment();
		
		if(s == null) return null;
		
		return s.getCurrentNode();
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
	
	public int lengthInNodes() {
		int sum = 0;
		
		for(PathSegment s : segments) {
			
			sum += s.length();
		}
		
		return sum;
	}
	
	public int length() {
		return segments.size();
	}
	
	public boolean isImpossible() {
		for(int i = 0; i < OBSTACLE_LOOKAHEAD; i++) {
			
			Movement m = getNextMovement(i);
			
			if(m == null) break;
			
			if(m.isImpossible()) return true;
		}
		
		return false;
	}
	
	public boolean isFinished() {
		return state != PathState.PAUSED;
	}
	
	public boolean isEmpty() {
		return segments.isEmpty();
	}
	
	public List<PathSegment> getAllSegments() {
		return segments;
	}
	
	public PathState getState() {
		return state;
	}
	
	public void setState(PathState state) {
		this.state = state;
	}
	
}
