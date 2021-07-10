package de.stylextv.lynx.option;

import de.stylextv.lynx.Lynx;

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
		
		Lynx.getInstance().getOptions().save();
	}
	
}
