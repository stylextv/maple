package de.stylextv.dwarf.task;

public abstract class Cycle {
	
	private static final Cycle[] CYCLES = new Cycle[] {
			new MiningCycle()
	};
	
	private String name;
	
	public Cycle(String name) {
		this.name = name;
	}
	
	public abstract void start(String[] args);
	
	public abstract boolean run();
	
	public String getName() {
		return name;
	}
	
	public static Cycle getCycle(String name) {
		for(Cycle c : CYCLES) {
			if(c.getName().equalsIgnoreCase(name)) return c;
		}
		
		return null;
	}
	
}
