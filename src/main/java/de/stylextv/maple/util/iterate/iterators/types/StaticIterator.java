package de.stylextv.maple.util.iterate.iterators.types;

import de.stylextv.maple.util.iterate.FunctionIterator;

public abstract class StaticIterator<T> extends FunctionIterator<T> {
	
	private Object[] elements;
	
	public StaticIterator(int length) {
		super(length);
		
		this.elements = new Object[length];
		
		generate();
	}
	
	public abstract void generate();
	
	public void set(int index, T t) {
		elements[index] = t;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		return (T) elements[index];
	}
	
}
