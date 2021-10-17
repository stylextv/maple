package de.stylextv.maple.util.iterate;

public abstract class IntPairIterator extends FunctionIterator<IntPair> {
	
	private IntPair[] pairs;
	
	public IntPairIterator(int length) {
		super(length);
		
		this.pairs = new IntPair[length];
		
		generate();
	}
	
	public abstract void generate();
	
	public void setPair(int index, IntPair pair) {
		pairs[index] = pair;
	}
	
	@Override
	public IntPair get(int index) {
		return pairs[index];
	}
	
}
