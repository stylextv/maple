package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntIterator;

public class RangeIterator extends IntIterator {
	
	// TODO common range iterators as constants
	
	public RangeIterator(int length) {
		super(length);
	}
	
	@Override
	public Integer get(int index) {
		return index;
	}
	
}
