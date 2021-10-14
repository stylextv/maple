package de.stylextv.maple.world.scan.entity.filters;

import de.stylextv.maple.world.scan.entity.EntityFilter;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

public class EntityNameFilter extends EntityFilter {
	
	private String name;
	
	public EntityNameFilter(String name) {
		this.name = name;
	}
	
	@Override
	public boolean matches(Entity e) {
		Text text = e.getCustomName();
		
		if(text == null) return false;
		
		String s = text.asString();
		
		return s.equals(name);
	}
	
	public String getName() {
		return name;
	}
	
}
