package de.stylextv.lynx.pathing.calc.favoring;

import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.PathSegment;
import de.stylextv.lynx.util.world.CoordUtil;
import de.stylextv.lynx.world.avoidance.Avoidance;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;

public class Favoring {
	
	private static final double BACKTRACKING_COEFFICIENT = 0.5;
	
	private static final long UPDATE_DELAY = 2000;
	
	private static Favoring defaultFavoring;
	
	private static long lastUpdate;
	
	private Long2DoubleOpenHashMap map;
	
	public Favoring(PathSegment s) {
		this();
		
		if(s != null) applyBacktracking(s);
	}
	
	public Favoring() {
		map = new Long2DoubleOpenHashMap(512);
		
		map.defaultReturnValue(1);
		
		applyAvoidance();
	}
	
	public void applyBacktracking(PathSegment s) {
		for(int i = 0; i < s.length(); i++) {
			
			Node n = s.getNode(i);
			
			setCoefficient(n, BACKTRACKING_COEFFICIENT);
		}
	}
	
	public void applyAvoidance() {
		for(Avoidance a : Avoidance.list()) {
			
			a.apply(map);
		}
	}
	
	public double getCoefficient(Node n) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return getCoefficient(x, y, z);
	}
	
	public double getCoefficient(int x, int y, int z) {
		long hash = CoordUtil.posAsLong(x, y, z);
		
		return map.get(hash);
	}
	
	public void setCoefficient(Node n, double d) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		setCoefficient(x, y, z, d);
	}
	
	public void setCoefficient(int x, int y, int z, double d) {
		long hash = CoordUtil.posAsLong(x, y, z);
		
		map.put(hash, d);
	}
	
	public static Favoring getDefault() {
		long time = System.currentTimeMillis();
		
		long dt = time - lastUpdate;
		
		if(dt > UPDATE_DELAY) {
			
			lastUpdate = time;
			
			defaultFavoring = new Favoring();
		}
		
		return defaultFavoring;
	}
	
}
