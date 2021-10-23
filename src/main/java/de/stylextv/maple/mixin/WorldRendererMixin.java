package de.stylextv.maple.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.RenderWorldEvent;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	
	@Inject(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "net/minecraft/client/render/BufferBuilderStorage.getEntityVertexConsumers"
			)
	)
	private void render(MatrixStack stack, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager textureManager, Matrix4f matrix, CallbackInfo info) {
		EventBus.onEvent(new RenderWorldEvent(RenderWorldEvent.Type.ENTITIES, stack));
	}
	
}
