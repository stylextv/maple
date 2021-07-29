package de.stylextv.lynx.pathing.calc;

import java.util.ArrayList;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.core.BlockPos;

public class Path {
	
	private static final int MINIMAL_NODE_AMOUNT = 30;
	
	private ArrayList<PathSegment> segments = new ArrayList<>();
	
	private PathSegment segment;
	
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
		
		return l < MINIMAL_NODE_AMOUNT;
	}
	
	public int nodesLeft() {
		PathSegment s = getSegment();
		
		if(s == null) return 0;
		
		return s.nodesLeft();
	}
	
	public boolean isFinished() {
		return getSegment() == null;
	}
	
	public Node getCurrentNode() {
		PathSegment s = getSegment();
		
		if(s == null) return null;
		
		return s.getCurrentNode();
	}
	
	public PathSegment getSegment() {
		if(segment == null || segment.isFinished()) nextSegment();
		
		return segment;
	}
	
	public ArrayList<PathSegment> getQueuedSegments() {
		return segments;
	}
	
}
