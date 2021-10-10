package de.stylextv.maple.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.ClientTickEvent;
import de.stylextv.maple.event.events.WorldEvent;
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
		EventBus.onEvent(new WorldEvent(WorldEvent.Type.LOAD, world));
	}
	
	@Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
	private void disconnect(Screen screen, CallbackInfo info) {
		ClientWorld world = WorldContext.world();
		
		if(world != null) EventBus.onEvent(new WorldEvent(WorldEvent.Type.UNLOAD, world));
	}
	
}
