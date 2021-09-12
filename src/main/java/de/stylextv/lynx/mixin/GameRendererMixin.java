package de.stylextv.lynx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.lynx.event.EventBus;
import de.stylextv.lynx.event.events.RenderTickEvent;
import de.stylextv.lynx.event.events.RenderWorldEvent;
import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	
	@Inject(method = "render(FJZ)V", at = @At("HEAD"))
	private void render(CallbackInfo info) {
		EventBus.onEvent(new RenderTickEvent());
	}
	
	@Inject(method = "renderWorld(FJZ)V", at = @At("HEAD"))
	private void renderWorld(CallbackInfo info) {
		EventBus.onEvent(new RenderWorldEvent(null));
	}
	
}
