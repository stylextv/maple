package de.stylextv.dwarf.input;

public class SmoothInput {
	
	private double state;
	
	private boolean active;
	
	public void feedInput(double d) {
		active = true;
		
		float deltaTime = PlayerContext.deltaTime();
		
		state += d;
	}
	
	public double getState() {
		return state;
	}
	
	public void setState(double state) {
		this.state = state;
	}
	
	public boolean isActive() {
		if(!active) return false;
		
		active = false;
		
		return true;
	}
	
}
