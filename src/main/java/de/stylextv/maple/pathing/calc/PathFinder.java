package de.stylextv.maple.pathing.calc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.pathing.calc.favoring.Favoring;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.calc.openset.HeapOpenSet;
import de.stylextv.maple.pathing.movement.Move;
import de.stylextv.maple.pathing.movement.Movement;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;

public class PathFinder {
	
	private static final int MAX_CHUNK_BORDER_HITS = 50;
	
	private static final float[] PS_COEFFICIENTS = {1.5f, 2, 2.5f, 3, 4, 5, 10};
	
	private static final int PS_MIN_DISTANCE = 25;
	
	private Long2ObjectOpenHashMap<Node> map;
	
	private HeapOpenSet openSet;
	
	private Set<Node> closedSet;
	
	private Node[] partialSolutions;
	
	private Goal goal;
	
	private Favoring favoring;
	
	private int chunkBorderHits;
	
	private Node startNode;
	
	private Node lastConsideration;
	
	private boolean stop;
	
	private boolean pause;
	
	private boolean failing = true;
	
	public PathFinder(Goal goal, Favoring favoring) {
		this.goal = goal;
		this.favoring = favoring;
		
		this.map = new Long2ObjectOpenHashMap<>(1024, 0.75f);
		
		this.openSet = new HeapOpenSet();
		
		this.closedSet = new HashSet<>();
		
		this.partialSolutions = new Node[PS_COEFFICIENTS.length];
	}
	
	public PathSegment find(BlockPos start, long primaryTimeout, long failureTimeout) {
		return find(start.getX(), start.getY(), start.getZ(), primaryTimeout, failureTimeout);
	}
	
	public PathSegment find(int startX, int startY, int startZ, long primaryTimeout, long failureTimeout) {
		long startTime = System.currentTimeMillis();
		
		startNode = getMapNode(startX, startY, startZ);
		
		openSet.add(startNode);
		
		while(!openSet.isEmpty() && !stop) {
			Node n = openSet.poll();
			
			lastConsideration = n;
			
			closedSet.add(n);
			
			updatePartialSolutions(n);
			
			if(goal.isFinalNode(n)) {
				
				return backtrace(n);
				
			} else {
				addAdjacentNodes(n);
			}
			
			long now = System.currentTimeMillis();
			
			long elapsedTime = now - startTime;
			
			boolean ranOutOfTime = elapsedTime > failureTimeout || (!failing && elapsedTime > primaryTimeout);
			
			if(chunkBorderHits > MAX_CHUNK_BORDER_HITS || ranOutOfTime) {
				
				pause = true;
				
				break;
			}
		}
		
		if(stop) return null;
		
		return bestSoFar();
	}
	
	public PathSegment bestSoFar() {
		if(startNode == null) return null;
		
		for(Node n : partialSolutions) {
			
			if(n == null) continue;
			
			int dis = startNode.squaredDistanceTo(n);
			
			boolean b = dis > PS_MIN_DISTANCE;
			
			if(b) return backtrace(n);
		}
		
		return null;
	}
	
	public PathSegment lastConsideredPath() {
		if(lastConsideration == null) return null;
		
		return backtrace(lastConsideration);
	}
	
	public void stop() {
		stop = true;
	}
	
	private void updatePartialSolutions(Node n) {
		for(int i = 0; i < PS_COEFFICIENTS.length; i++) {
			
			Node closest = partialSolutions[i];
			
			boolean closer = true;
			
			if(closest != null) {
				
				float f = PS_COEFFICIENTS[i];
				
				closer = n.getPartialCost(f) < closest.getPartialCost(f);
			}
			
			if(closer) {
				
				partialSolutions[i] = n;
				
				int dis = startNode.squaredDistanceTo(n);
				
				if(dis > PS_MIN_DISTANCE) failing = false;
			}
		}
	}
	
	private PathSegment backtrace(Node n) {
		List<Movement> list = new ArrayList<Movement>();
		
		while(true) {
			Node parent = n.getParent();
			
			if(parent == null) break;
			
			list.add(0, n.getMovement());
			
			n = parent;
		}
		
		return new PathSegment(list);
	}
	
	private void addAdjacentNodes(Node node) {
		for(Move move : Move.getAllMoves()) {
			
			Movement m = move.apply(node, this);
			
			if(m != null) addAdjacentNode(node, m);
		}
	}
	
	private void addAdjacentNode(Node parent, Movement m) {
		Node n = m.getDestination();
		
		BlockType type = n.getType();
		
		if(type.isUnloaded() || closedSet.contains(n)) return;
		
		double cost = m.favoredCost(favoring);
		
		if(cost >= Cost.INFINITY) return;
		
		if(n.isOpen()) {
			
			if(n.updateParent(parent, m, cost)) {
				openSet.update(n);
			}
			
		} else {
			
			n.setParent(parent, m, cost);
			
			openSet.add(n);
		}
	}
	
	public Node getAdjacentNode(Node n, int dx, int dy, int dz) {
		int x = n.getX() + dx;
		int y = n.getY() + dy;
		int z = n.getZ() + dz;
		
		return getMapNode(x, y, z);
	}
	
	private Node getMapNode(int x, int y, int z) {
		long hash = Node.posAsLong(x, y, z);
		
		Node n = map.get(hash);
		
		if(n != null) return n;
		
		n = new Node(x, y, z);
		
		n.updateHeuristic(goal);
		
		map.put(hash, n);
		
		if(n.getType().isUnloaded()) chunkBorderHits++;
		
		return n;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
	public Favoring getFavoring() {
		return favoring;
	}
	
	public boolean wasStopped() {
		return stop;
	}
	
	public boolean wasPaused() {
		return pause;
	}
	
	public boolean isFailing() {
		return failing;
	}
	
}
