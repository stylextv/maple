package de.stylextv.maple.util.iterate.iterators;

import de.stylextv.maple.util.iterate.IntPair;
import de.stylextv.maple.util.iterate.IntPairIterator;

public class SpiralIterator extends IntPairIterator {
	
	private int size;
	
	private IntPair[] pairs;
	
	public SpiralIterator(int size) {
		super(size * size);
		
		this.size = size;
		
		generate();
	}
	
	private void generate() {
		int x = 0;
		int y = 0;
		
		int dx = 0;
		int dy = -1;
		
		int l = size * size;
		
		for(int i = 0; i < l; i++) {
			
			pairs[i] = new IntPair(x, y);
			
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
	
	@Override
	public IntPair get(int index) {
		return pairs[index];
	}
	
	public int getSize() {
		return size;
	}
	
}
