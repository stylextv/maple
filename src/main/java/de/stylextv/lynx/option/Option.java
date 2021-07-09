package de.stylextv.lynx.option;

public class Option<T> {
	
	private transient T defaultValue;
	
	private T value;
	
	public Option(T value) {
		this.defaultValue = value;
		this.value = value;
	}
	
	public void reset() {
		value = defaultValue;
	}
	
	public boolean wasModified() {
		return value.equals(defaultValue);
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
}
