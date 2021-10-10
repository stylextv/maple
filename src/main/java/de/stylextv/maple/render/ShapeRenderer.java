package de.stylextv.maple.render;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.scheme.Color;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vector4f;

public class ShapeRenderer {
	
	private static Tessellator tessellator;
	
	private static BufferBuilder builder;
	
	public static void drawBox(RenderWorldEvent event, BlockPos pos, Color color, int lineWidth) {
		drawBox(event, pos, pos, color, lineWidth);
	}
	
	public static void drawBox(RenderWorldEvent event, BlockPos pos1, BlockPos pos2, Color color, int lineWidth) {
		float x1 = Math.min(pos1.getX(), pos2.getX());
		float y1 = Math.min(pos1.getY(), pos2.getY());
		float z1 = Math.min(pos1.getZ(), pos2.getZ());
		
		float x2 = Math.max(pos1.getX(), pos2.getX()) + 1;
		float y2 = Math.max(pos1.getY(), pos2.getY()) + 1;
		float z2 = Math.max(pos1.getZ(), pos2.getZ()) + 1;
		
		Vec3f[][][] vertices = new Vec3f[2][2][2];
		
		for(int x = 0; x < 2; x++) {
			for(int y = 0; y < 2; y++) {
				for(int z = 0; z < 2; z++) {
					
					float vx = x1 + (x2 - x1) * x;
					float vy = y1 + (y2 - y1) * y;
					float vz = z1 + (z2 - z1) * z;
					
					vertices[x][y][z] = new Vec3f(vx, vy, vz);
				}
			}
		}
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				drawLine(event, new Vec3f[] {vertices[0][i][j], vertices[1][i][j]}, color, lineWidth);
				drawLine(event, new Vec3f[] {vertices[i][0][j], vertices[i][1][j]}, color, lineWidth);
				drawLine(event, new Vec3f[] {vertices[i][j][0], vertices[i][j][1]}, color, lineWidth);
			}
		}
	}
	
	public static void drawNodeConnection(RenderWorldEvent event, Node n, Color color, float width) {
		Node parent = n.getParent();
		
		float x = n.getX() + 0.5f;
		float y = n.getY() + 0.5f;
		float z = n.getZ() + 0.5f;
		
		float px = parent.getX() + 0.5f;
		float py = parent.getY() + 0.5f;
		float pz = parent.getZ() + 0.5f;
		
		Vec3f[] vertices = new Vec3f[] {
				new Vec3f(x, y, z),
				new Vec3f(px, py, pz)
		};
		
		drawLine(event, vertices, color, width);
	}
	
	public static void drawLine(RenderWorldEvent event, Vec3f[] vertices, Color color, float width) {
		drawLine(event, vertices, color, width, true);
	}
	
	public static void drawLine(RenderWorldEvent event, Vec3f[] vertices, Color color, float width, boolean joint) {
		RenderSystem.lineWidth(width);
		
		DrawMode mode = joint ? DrawMode.DEBUG_LINE_STRIP : DrawMode.DEBUG_LINES;
		
		beginShape(event, mode, VertexFormats.POSITION_COLOR);
		
		endShape(vertices, color);
		
		RenderSystem.lineWidth(1);
	}
	
	private static void beginShape(RenderWorldEvent event, DrawMode mode, VertexFormat format) {
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		
		MatrixStack stack = RenderSystem.getModelViewStack();
		
		stack.push();
		
		Entry entry = event.getMatrixStack().peek();
		
		Matrix4f matrix = entry.getModel();
		
		stack.method_34425(matrix);
		
		RenderSystem.applyModelViewMatrix();
		
		RenderSystem.disableDepthTest();
		
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		
		RenderSystem.disableTexture();
		RenderSystem.disableBlend();
		
		tessellator = Tessellator.getInstance();
		
		builder = tessellator.getBuffer();
		
		builder.begin(mode, format);
	}
	
	private static void endShape(Vec3f[] vertices, Color color) {
		Vector4f colorVector = color.asVector();
		
		float r = colorVector.getX();
		float g = colorVector.getY();
		float b = colorVector.getZ();
		float a = colorVector.getW();
		
		Vec3d pos = GameContext.cameraPosition();
		
		for(Vec3f v : vertices) {
			
			double x = v.getX() - pos.getX();
			double y = v.getY() - pos.getY();
			double z = v.getZ() - pos.getZ();
			
			builder.vertex(x, y, z).color(r, g, b, a).next();
		}
		
		tessellator.draw();
		
		MatrixStack stack = RenderSystem.getModelViewStack();
		
		stack.pop();
		
		RenderSystem.applyModelViewMatrix();
		
		RenderSystem.enableBlend();
		RenderSystem.enableTexture();
	}
	
}
