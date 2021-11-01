package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntTriplet;
import de.stylextv.maple.util.iterate.iterators.types.IntTripletIterator;

public class BoxIterator extends IntTripletIterator {
	
	// TODO most common box iterators as constants
	
	private int width;
	private int height;
	private int length;
	
	public BoxIterator(int size) {
		this(size, size, size);
	}
	
	public BoxIterator(int width, int height, int length) {
		super(width * height * length);
		
		this.width = width;
		this.height = height;
		this.length = length;
	}
	
	@Override
	public void generate() {
		int m = (width * width);
		
		for(int i = 0; i < getLength(); i++) {
			
			int y = i / m;
			
			int j = i - y * m;
			
			int x = j % width;
			int z = j / width;
			
			set(i, new IntTriplet(x, y, z));
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getLength() {
		return length;
	}
	
}
