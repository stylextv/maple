package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntIterator;

public class RangeIterator extends IntIterator {
	
	// TODO common range iterators as constants
	
	private int from;
	private int to;
	
	public RangeIterator(int length) {
		this(0, length - 1);
	}
	
	public RangeIterator(int from, int to) {
		super(to - from + 1);
		
		this.from = from;
		this.to = to;
	}
	
	@Override
	public Integer get(int index) {
		return from + index;
	}
	
	public int getFrom() {
		return from;
	}
	
	public int getTo() {
		return to;
	}
	
}
