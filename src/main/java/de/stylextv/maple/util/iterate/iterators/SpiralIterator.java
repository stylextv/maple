package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntPair;
import de.stylextv.maple.util.iterate.iterators.types.IntPairIterator;

public class SpiralIterator extends IntPairIterator {
	
	private int size;
	
	public SpiralIterator(int size) {
		super(size * size);
		
		this.size = size;
	}
	
	@Override
	public void generate() {
		int x = 0;
		int y = 0;
		
		int dx = 0;
		int dy = -1;
		
		for(int i = 0; i < getLength(); i++) {
			
			set(i, new IntPair(x, y));
			
			boolean b1 = x < 0 && x == -y;
			boolean b2 = x > 0 && x == 1 - y;
			
			boolean turn = b1 || b2 || x == y;
			
			if(turn) {
				
				int temp = dx;
				
				dx = -dy;
				dy = temp;
			}
			
			x += dx;
			y += dy;
		}
	}
	
	public int getSize() {
		return size;
	}
	
}
