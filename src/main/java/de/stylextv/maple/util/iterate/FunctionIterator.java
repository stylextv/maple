package de.stylextv.maple.util.iterate;

import java.util.Iterator;

public abstract class FunctionIterator<T> implements Iterable<T> {
	
	private int length;
	
	public FunctionIterator(int length) {
		this.length = length;
	}
	
	public abstract T get(int index);
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private int index;
			
			@Override
			public boolean hasNext() {
				return index < length;
			}
			
			@Override
			public T next() {
				T t = get(index);
				
				index++;
				
				return t;
			}
			
		};
	}
	
	public int getLength() {
		return length;
	}
	
}
