package de.stylextv.lynx.module;

import de.stylextv.lynx.resource.Texture;

public class Module {
	
	private String id;
	
	private String name;
	
	private Texture icon;
	
	public Module(String id, String name) {
		this.id = id;
		this.name = name;
		
		this.icon = new Texture("textures/gui/modules/" + id);
	}
	
	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Texture getIcon() {
		return icon;
	}
	
}
