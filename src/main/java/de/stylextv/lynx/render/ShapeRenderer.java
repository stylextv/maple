package de.stylextv.lynx.render;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Vector4f;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.scheme.Color;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class ShapeRenderer {
	
	private static final float BOX_VERTEX_OFFSET = 0.0075f;
	
	private static Tesselator tesselator;
	
	private static BufferBuilder builder;
	
	public static void drawBox(RenderWorldLastEvent event, BlockPos pos, Color color, int lineWidth) {
		drawBox(event, pos, pos, color, lineWidth);
	}
	
	public static void drawBox(RenderWorldLastEvent event, BlockPos pos1, BlockPos pos2, Color color, int lineWidth) {
		float x1 = Math.min(pos1.getX(), pos2.getX()) - BOX_VERTEX_OFFSET;
		float y1 = Math.min(pos1.getY(), pos2.getY()) - BOX_VERTEX_OFFSET;
		float z1 = Math.min(pos1.getZ(), pos2.getZ()) - BOX_VERTEX_OFFSET;
		
		float x2 = Math.max(pos1.getX(), pos2.getX()) + 1 + BOX_VERTEX_OFFSET;
		float y2 = Math.max(pos1.getY(), pos2.getY()) + 1 + BOX_VERTEX_OFFSET;
		float z2 = Math.max(pos1.getZ(), pos2.getZ()) + 1 + BOX_VERTEX_OFFSET;
		
		Vec3[][][] vertices = new Vec3[2][2][2];
		
		for(int x = 0; x < 2; x++) {
			for(int y = 0; y < 2; y++) {
				for(int z = 0; z < 2; z++) {
					
					float vx = x1 + (x2 - x1) * x;
					float vy = y1 + (y2 - y1) * y;
					float vz = z1 + (z2 - z1) * z;
					
					vertices[x][y][z] = new Vec3(vx, vy, vz);
				}
			}
		}
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				drawLine(event, new Vec3[] {vertices[0][i][j], vertices[1][i][j]}, color, lineWidth);
				drawLine(event, new Vec3[] {vertices[i][0][j], vertices[i][1][j]}, color, lineWidth);
				drawLine(event, new Vec3[] {vertices[i][j][0], vertices[i][j][1]}, color, lineWidth);
			}
		}
	}
	
	public static void drawNodeChain(RenderWorldLastEvent event, Node n, Color color) {
		drawNodeChain(event, n, Integer.MAX_VALUE, color);
	}
	
	public static void drawNodeChain(RenderWorldLastEvent event, Node n, int length, Color color) {
		if(n == null) return;
		
		int i = 0;
		
		while(n.getParent() != null && i < length) {
			
			Node parent = n.getParent();
			
			float x = n.getX() + 0.5f;
			float y = n.getY() + 0.5f;
			float z = n.getZ() + 0.5f;
			
			float px = parent.getX() + 0.5f;
			float py = parent.getY() + 0.5f;
			float pz = parent.getZ() + 0.5f;
			
			Vec3[] vertices = new Vec3[] {
					new Vec3(x, y, z),
					new Vec3(px, py, pz)
			};
			
			ShapeRenderer.drawLine(event, vertices, color, 2);
			
			n = parent;
			
			i++;
		}
	}
	
	public static void drawLine(RenderWorldLastEvent event, Vec3[] vertices, Color color, float width) {
		drawLine(event, vertices, color, width, true);
	}
	
	public static void drawLine(RenderWorldLastEvent event, Vec3[] vertices, Color color, float width, boolean joint) {
		RenderSystem.lineWidth(width);
		
		Mode mode = joint ? Mode.DEBUG_LINE_STRIP : Mode.DEBUG_LINES;
		
		beginShape(event, mode, DefaultVertexFormat.POSITION_COLOR);
		
		endShape(vertices, color);
		
		RenderSystem.lineWidth(1);
	}
	
	private static void beginShape(RenderWorldLastEvent event, Mode mode, VertexFormat format) {
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		
		PoseStack stack = RenderSystem.getModelViewStack();
		
		stack.pushPose();
		
		Pose pose = event.getMatrixStack().last();
		
		stack.mulPoseMatrix(pose.pose());
		
		RenderSystem.applyModelViewMatrix();
		
		RenderSystem.disableDepthTest();
		
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		
		RenderSystem.disableTexture();
		RenderSystem.disableBlend();
		
		tesselator = Tesselator.getInstance();
		
		builder = tesselator.getBuilder();
		
		builder.begin(mode, format);
	}
	
	private static void endShape(Vec3[] vertices, Color color) {
		Vector4f colorVector = color.asVector();
		
		float r = colorVector.x();
		float g = colorVector.y();
		float b = colorVector.z();
		float a = colorVector.w();
		
		Vec3 pos = GameContext.cameraPosition();
		
		for(Vec3 v : vertices) {
			
			double x = v.x() - pos.x();
			double y = v.y() - pos.y();
			double z = v.z() - pos.z();
			
			builder.vertex(x, y, z).color(r, g, b, a).endVertex();
		}
		
		tesselator.end();
		
		PoseStack stack = RenderSystem.getModelViewStack();
		
		stack.popPose();
		
		RenderSystem.applyModelViewMatrix();
		
		RenderSystem.enableBlend();
		RenderSystem.enableTexture();
	}
	
}
