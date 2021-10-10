package de.stylextv.maple.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.EntityDeathEvent;
import de.stylextv.maple.event.events.EntitySleptEvent;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	
	@Inject(method = "setHealth(F)V", at = @At("TAIL"))
	private void setHealth(float health, CallbackInfo info) {
		if(health <= 0) {
			
			LivingEntity entity = (LivingEntity) (Object) this;
			
			EventBus.onEvent(new EntityDeathEvent(entity));
		}
	}
	
	@Inject(method = "wakeUp()V", at = @At("TAIL"))
	private void wakeUp(CallbackInfo info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		
		EventBus.onEvent(new EntitySleptEvent(entity));
	}
	
}
