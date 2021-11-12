package de.stylextv.maple.render;

import com.mojang.blaze3d.systems.RenderSystem;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.event.events.RenderWorldEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public class NameTagRenderer {
	
	private static final TextRenderer TEXT_RENDERER = GameContext.textRenderer();
	
	public static void drawTag(RenderWorldEvent event, String s, float x, float y, float z) {
		MatrixStack stack = event.getMatrixStack();
		
		MinecraftClient client = GameContext.client();
		
		EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
		
		Vec3d v = GameContext.cameraPosition();
		
		double renderX = x - v.getX();
		double renderY = y - v.getY();
		double renderZ = z - v.getZ();
		
		stack.push();
		
		stack.translate(renderX, renderY, renderZ);
		
		Quaternion orientation = dispatcher.getRotation();
		
		BufferBuilderStorage builders = client.getBufferBuilders();
		
		Immediate consumers = builders.getEntityVertexConsumers();
		
		stack.multiply(orientation);
		
		stack.scale(-0.025F, -0.025F, 0.025F);
		
		Matrix4f matrix = stack.peek().getPositionMatrix();
		
		int width = TEXT_RENDERER.getWidth(s);
		
		int offX = -width / 2;
		int offY = 0;
		
		float f = GameContext.options().getTextBackgroundOpacity(0.25F);
		
		int rgb = (int) (f * 255) << 24;
		
		TEXT_RENDERER.draw(s, offX, offY, 0, false, matrix, consumers, false, rgb, 255);
		
		stack.pop();
		
		RenderSystem.enableDepthTest();
	}
	
}
