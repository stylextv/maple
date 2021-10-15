package de.stylextv.maple.world.scan;

public class ScanFilters<T extends ScanFilter<V>, V> {
	
	private T[] filters;
	
	@SafeVarargs
	public ScanFilters(T... filters) {
		this.filters = filters;
	}
	
	public boolean matches(V v) {
		for(T filter : filters) {
			
			if(!filter.matches(v)) return false;
		}
		
		return true;
	}
	
	public T[] getFilters() {
		return filters;
	}
	
}
