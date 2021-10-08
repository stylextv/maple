package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;
import net.minecraft.entity.LivingEntity;

public class EntityDeathEvent extends Event {
	
	private LivingEntity entity;
	
	public EntityDeathEvent(LivingEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onEntityDeath(this);
	}
	
	public LivingEntity getEntity() {
		return entity;
	}
	
}
