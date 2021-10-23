package de.stylextv.maple.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.TickEvent;
import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	
	@Inject(method = "render(FJZ)V", at = @At("HEAD"))
	private void render(float tickDelta, long startTime, boolean tick, CallbackInfo info) {
		EventBus.onEvent(new TickEvent(TickEvent.Type.RENDER));
	}
	
}
