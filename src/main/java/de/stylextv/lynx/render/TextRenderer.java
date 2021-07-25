package de.stylextv.lynx.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;

import de.stylextv.lynx.context.GameContext;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class TextRenderer {
	
	private static final Font FONT = GameContext.font();
	
	public static void drawText(RenderWorldLastEvent event, String s, float x, float y, float z) {
		PoseStack stack = event.getMatrixStack();
		
		Vec3 v = GameContext.cameraPosition();
		
		EntityRenderDispatcher dispatcher = GameContext.minecraft().getEntityRenderDispatcher();
		
		Quaternion orientation = dispatcher.cameraOrientation();
		
		double renderX = x - v.x();
		double renderY = y - v.y();
		double renderZ = z - v.z();
		
		RenderBuffers buffers = GameContext.minecraft().renderBuffers();
		
		BufferSource source = buffers.bufferSource();
		
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
		
		FONT.drawInBatch(s, offX, offY, 0, false, matrix, source, false, rgb, 0);
		
		source.endBatch();
		
		stack.popPose();
		
		RenderSystem.enableDepthTest();
	}
	
}
