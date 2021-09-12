package de.stylextv.lynx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.lynx.event.EventBus;
import de.stylextv.lynx.event.events.ClientTickEvent;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	
	@Inject(at = @At("HEAD"), method = "tick()V")
	private void tick(CallbackInfo info) {
		EventBus.onEvent(new ClientTickEvent());
	}
	
}
