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
		int x = index % width;
		int y = index / width;
		
		return new IntPair(x, y);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
