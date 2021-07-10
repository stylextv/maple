package de.stylextv.lynx.command;

public abstract class Command {
	
	private String name;
	
	private String description;
	
	public Command(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public abstract boolean execute(String[] args);
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String[] getUsages() {
		return null;
	}
	
}
