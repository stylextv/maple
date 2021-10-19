package de.stylextv.maple.render;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.render.mesh.BoxMesh;
import de.stylextv.maple.render.mesh.LineMesh;
import de.stylextv.maple.render.mesh.Mesh;
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
		int disX = Math.abs(pos2.getX() - pos1.getX());
		int disY = Math.abs(pos2.getY() - pos1.getY());
		int disZ = Math.abs(pos2.getZ() - pos1.getZ());
		
		int width = disX + 1;
		int height = disY + 1;
		int length = disZ + 1;
		
		Mesh mesh = BoxMesh.getMesh(width, height, length);
		
		drawMesh(event, mesh, pos1, color, lineWidth);
	}
	
	public static void drawNodeConnection(RenderWorldEvent event, Node n, Color color, float lineWidth) {
		Node parent = n.getParent();
		
		int dx = n.getX() - parent.getX();
		int dy = n.getY() - parent.getY();
		int dz = n.getZ() - parent.getZ();
		
		Mesh mesh = LineMesh.getMesh(dx, dy, dz);
		
		BlockPos pos = parent.blockPos();
		
		Vec3d v = Vec3d.ofCenter(pos);
		
		drawMesh(event, mesh, new Vec3f(v), color, lineWidth);
	}
	
	public static void drawLine(RenderWorldEvent event, Vec3f v1, Vec3f v2, Color color, float lineWidth) {
		float dx = v2.getX() - v1.getX();
		float dy = v2.getY() - v1.getY();
		float dz = v2.getZ() - v1.getZ();
		
		Mesh mesh = new LineMesh(dx, dy, dz);
		
		drawMesh(event, mesh, v1, color, lineWidth);
	}
	
	public static void drawMesh(RenderWorldEvent event, Mesh mesh, BlockPos pos, Color color, float lineWidth) {
		Vec3d v = Vec3d.of(pos);
		
		drawMesh(event, mesh, new Vec3f(v), color, lineWidth);
	}
	
	public static void drawMesh(RenderWorldEvent event, Mesh mesh, Vec3f pos, Color color, float lineWidth) {
		RenderSystem.lineWidth(lineWidth);
		
		DrawMode mode = DrawMode.DEBUG_LINES;
		
		beginShape(event, mode, VertexFormats.POSITION_COLOR);
		
		endShape(mesh, pos, color);
		
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
	
	private static void endShape(Mesh mesh, Vec3f pos, Color color) {
		Vector4f colorVector = color.asVector();
		
		float r = colorVector.getX();
		float g = colorVector.getY();
		float b = colorVector.getZ();
		float a = colorVector.getW();
		
		Vec3d cameraPos = GameContext.cameraPosition();
		
		Vec3f[] vertices = mesh.getVertices();
		
		for(Vec3f v : vertices) {
			
			double x = v.getX() + pos.getX() - cameraPos.getX();
			double y = v.getY() + pos.getY() - cameraPos.getY();
			double z = v.getZ() + pos.getZ() - cameraPos.getZ();
			
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
