package de.stylextv.maple.command;

public abstract class Command {
	
	private String name;
	
	private String[] aliases;
	
	private String description;
	
	public Command(String name, String description, String... aliases) {
		this.name = name;
		this.aliases = aliases;
		this.description = description;
	}
	
	public abstract boolean execute(String[] args);
	
	public boolean nameEquals(String s) {
		if(name.equalsIgnoreCase(s)) return true;
		
		for(String alias : aliases) {
			if(alias.equalsIgnoreCase(s)) return true;
		}
		
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getAliases() {
		return aliases;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String[] getUsages() {
		return null;
	}
	
}
