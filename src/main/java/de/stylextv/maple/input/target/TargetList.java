package de.stylextv.maple.input.target;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.pathing.calc.goal.TwoBlocksGoal;
import net.minecraft.util.math.BlockPos;

public class TargetList<T extends BlockTarget> implements Iterable<T> {
	
	private Set<T> set = ConcurrentHashMap.newKeySet();
	
	public void add(T target) {
		set.add(target);
	}
	
	public void remove(BlockPos pos) {
		T target = get(pos);
		
		if(target != null) remove(target);
	}
	
	public void remove(T target) {
		set.remove(target);
	}
	
	public boolean contains(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		return contains(x, y, z);
	}
	
	public boolean contains(int x, int y, int z) {
		return get(x, y, z) != null;
	}
	
	public T get(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		return get(x, y, z);
	}
	
	public T get(int x, int y, int z) {
		for(T target : set) {
			
			BlockPos pos = target.getPos();
			
			if(pos.getX() == x && pos.getY() == y && pos.getZ() == z) return target;
		}
		
		return null;
	}
	
	public CompositeGoal toGoal() {
		return CompositeGoal.fromCollection(set, t -> new TwoBlocksGoal(t.getPos()));
	}
	
	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}
	
	public int size() {
		return set.size();
	}
	
	public boolean isEmpty() {
		return set.isEmpty();
	}
	
	public Set<T> getSet() {
		return set;
	}
	
}
