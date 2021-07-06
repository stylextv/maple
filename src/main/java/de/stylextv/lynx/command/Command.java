package de.stylextv.lynx.command;

public abstract class Command {
	
	private String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public abstract void execute(String[] args);
	
	public String getName() {
		return name;
	}
	
}
