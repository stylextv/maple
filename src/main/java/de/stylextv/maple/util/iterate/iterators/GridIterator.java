package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntPair;
import de.stylextv.maple.util.iterate.IntPairIterator;

public class GridIterator extends IntPairIterator {
	
	// TODO common grid iterators as constants
	
	private int size;
	
	private Type type;
	
	public GridIterator(int size, Type type) {
		super(size * size);
		
		this.size = size;
		this.type = type;
	}
	
	@Override
	public IntPair get(int index) {
		if(type == Type.STANDARD) {
			
			int i = index % size;
			int j = index / size;
			
			return new IntPair(i, j);
		}
		
		if(type == Type.SPIRAL) {
			
//			int i = index % size;
//			int j = index / size;
			
			return new IntPair(i, j);
		}
		
		return null;
	}
	
	public int getSize() {
		return size;
	}
	
	public Type getType() {
		return type;
	}
	
	public enum Type {
		
		STANDARD,
		SPIRAL;
		
	}
	
}
