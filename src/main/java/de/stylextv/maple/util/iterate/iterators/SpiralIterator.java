package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntPair;
import de.stylextv.maple.util.iterate.IntPairIterator;

public class SpiralIterator extends IntPairIterator {
	
	private int size;
	
	public SpiralIterator(int size) {
		super(size * size);
		
		this.size = size;
	}
	
	@Override
	public IntPair get(int index) {
		int i = index % size;
		int j = index / size;
		
		return new IntPair(i, j);
	}
	
	public int getSize() {
		return size;
	}
	
}
