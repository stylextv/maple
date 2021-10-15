package de.stylextv.maple.world.scan;

import java.util.function.Predicate;

public class ScanFilter<T> {
	
	private Predicate<T> predicate;
	
	public ScanFilter() {}
	
	public ScanFilter(Predicate<T> predicate) {
		this.predicate = predicate;
	}
	
	public boolean matches(T t) {
		return predicate.test(t);
	}
	
}
