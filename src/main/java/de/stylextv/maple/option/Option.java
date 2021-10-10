package de.stylextv.maple.option;

import de.stylextv.maple.Maple;

public class Option<T> {
	
	private transient String name;
	
	private transient T defaultValue;
	
	private T value;
	
	public Option(String name, T value) {
		this.name = name;
		this.defaultValue = value;
		this.value = value;
		
		Options.registerOption(this);
	}
	
	public void reset() {
		setValue(defaultValue);
	}
	
	public boolean wasModified() {
		return !value.equals(defaultValue);
	}
	
	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
		
		Maple.getInstance().getOptions().save();
	}
	
}
