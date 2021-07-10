package de.stylextv.lynx.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import de.stylextv.lynx.context.GameContext;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class TextRenderer {
	
	private static final FontRenderer FONT = GameContext.font();
	
	public static void drawText(RenderWorldLastEvent event, String s, float x, float y, float z) {
		MatrixStack stack = event.getMatrixStack();
		
		Vector3d v = GameContext.cameraPosition();
		
		EntityRendererManager dispatcher = GameContext.minecraft().getEntityRenderDispatcher();
		
		Quaternion orientation = dispatcher.cameraOrientation();
		
		double renderX = x - v.x();
		double renderY = y - v.y();
		double renderZ = z - v.z();
		
		RenderTypeBuffers buffers = GameContext.minecraft().renderBuffers();
		
		Impl buffer = buffers.bufferSource();
		
		stack.pushPose();
		
		stack.translate(renderX, renderY, renderZ);
		stack.mulPose(orientation);
		
		stack.scale(-0.025F, -0.025F, 0.025F);
		
		Matrix4f matrix = stack.last().pose();
		
		int width = FONT.width(s);
		
		int offX = -width / 2;
		int offY = 0;
		
		float f = GameContext.settings().getBackgroundOpacity(0.25F);
		
        int rgb = (int) (f * 255) << 24;
		
		FONT.drawInBatch(s, offX, offY, 0, false, matrix, buffer, false, rgb, 0);
		
		buffer.endBatch();
		
		stack.popPose();
		
		RenderSystem.enableDepthTest();
	}
	
}
