package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntPair;
import de.stylextv.maple.util.iterate.IntPairIterator;

public class GridIterator extends IntPairIterator {
	
	// TODO common grid iterators as constants
	
	private int width;
	private int height;
	
	public GridIterator(int size) {
		this(size, size);
	}
	
	public GridIterator(int width, int height) {
		super(width * height);
		
		this.width = width;
		this.height = height;
	}
	
	@Override
	public IntPair get(int index) {
		int i = index % width;
		int j = index / width;
		
		return new IntPair(i, j);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
