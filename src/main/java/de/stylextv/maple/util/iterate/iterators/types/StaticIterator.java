package de.stylextv.maple.util.iterate.iterators.types;

import de.stylextv.maple.util.iterate.FunctionIterator;

public abstract class StaticIterator<T> extends FunctionIterator<T> {
	
	private Object[] elements;
	
	private boolean generated;
	
	public StaticIterator(int length) {
		super(length);
		
		this.elements = new Object[length];
	}
	
	public abstract void generate();
	
	public void set(int index, T t) {
		elements[index] = t;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		if(!generated) {
			
			generate();
			
			generated = true;
		}
		
		return (T) elements[index];
	}
	
	public boolean isGenerated() {
		return generated;
	}
	
}
