package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntPair;
import de.stylextv.maple.util.iterate.iterators.types.IntPairIterator;

public class GridIterator extends IntPairIterator {
	
	public static final GridIterator SIXTEEN_BY_SIXTEEN = new GridIterator(16, 16);
	
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
	public void generate() {
		for(int i = 0; i < getLength(); i++) {
			
			int x = i % width;
			int y = i / width;
			
			set(i, new IntPair(x, y));
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
