package de.stylextv.maple.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.event.events.TickEvent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	
	@Inject(method = "render(FJZ)V", at = @At("HEAD"))
	private void render(float tickDelta, long startTime, boolean tick, CallbackInfo info) {
		EventBus.onEvent(new TickEvent(TickEvent.Type.RENDER));
	}
	
	@Inject(
			method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V",
			at = @At(
					value = "INVOKE",
					target = "net/minecraft/client/render/WorldRenderer.render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V",
					shift = Shift.AFTER
			)
	)
	private void renderWorld(float tickDelta, long limitTime, MatrixStack stack, CallbackInfo info) {
		EventBus.onEvent(new RenderWorldEvent(stack));
	}
	
}
