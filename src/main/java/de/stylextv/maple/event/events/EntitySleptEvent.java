package de.stylextv.maple.event.events;

import de.stylextv.maple.event.Event;
import de.stylextv.maple.event.EventListener;
import net.minecraft.entity.LivingEntity;

public class EntitySleptEvent extends Event {
	
	private LivingEntity entity;
	
	public EntitySleptEvent(LivingEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onEntitySlept(this);
	}
	
	public LivingEntity getEntity() {
		return entity;
	}
	
}
