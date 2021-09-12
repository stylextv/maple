package de.stylextv.lynx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.event.EventBus;
import de.stylextv.lynx.event.events.ClientTickEvent;
import de.stylextv.lynx.event.events.WorldLoadEvent;
import de.stylextv.lynx.event.events.WorldUnloadEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	
	@Inject(method = "tick()V", at = @At("HEAD"))
	private void tick(CallbackInfo info) {
		EventBus.onEvent(new ClientTickEvent());
	}
	
	@Inject(method = "joinWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At("TAIL"))
	private void joinWorld(ClientWorld world, CallbackInfo info) {
		EventBus.onEvent(new WorldLoadEvent(world));
	}
	
	@Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
	private void disconnect(Screen screen, CallbackInfo info) {
		ClientWorld world = WorldContext.world();
		
		if(world != null) EventBus.onEvent(new WorldUnloadEvent(world));
	}
	
}
