package de.stylextv.lynx.render;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.calc.Path;
import de.stylextv.lynx.scheme.Color;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class ShapeRenderer {
	
	private static final float BOX_VERTEX_OFFSET = 0.0075f;
	
	private static final Vector2f[] RECT_VERTICES = new Vector2f[] {
			new Vector2f(0, 0),
			new Vector2f(1, 0),
			new Vector2f(1, 1),
			new Vector2f(0, 1)
	};
	
	private static Tessellator tessellator;
	
	private static BufferBuilder builder;
	
	private static MatrixStack stack;
	
	private static Matrix4f matrix;
	
	public static void drawBox(RenderWorldLastEvent event, BlockPos pos, Color color, Color outlineColor, int lineWidth) {
		drawBox(event, pos, pos, color, outlineColor, lineWidth);
	}
	
	public static void drawBox(RenderWorldLastEvent event, BlockPos pos1, BlockPos pos2, Color color, Color outlineColor, int lineWidth) {
		float x1 = Math.min(pos1.getX(), pos2.getX()) - BOX_VERTEX_OFFSET;
		float y1 = Math.min(pos1.getY(), pos2.getY()) - BOX_VERTEX_OFFSET;
		float z1 = Math.min(pos1.getZ(), pos2.getZ()) - BOX_VERTEX_OFFSET;
		
		float x2 = Math.max(pos1.getX(), pos2.getX()) + 1 + BOX_VERTEX_OFFSET;
		float y2 = Math.max(pos1.getY(), pos2.getY()) + 1 + BOX_VERTEX_OFFSET;
		float z2 = Math.max(pos1.getZ(), pos2.getZ()) + 1 + BOX_VERTEX_OFFSET;
		
		Vector3f[][][] vertices = new Vector3f[2][2][2];
		
		for(int x = 0; x < 2; x++) {
			for(int y = 0; y < 2; y++) {
				for(int z = 0; z < 2; z++) {
					
					float vx = x1 + (x2 - x1) * x;
					float vy = y1 + (y2 - y1) * y;
					float vz = z1 + (z2 - z1) * z;
					
					vertices[x][y][z] = new Vector3f(vx, vy, vz);
				}
			}
		}
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				drawLine(event, new Vector3f[] {vertices[0][i][j], vertices[1][i][j]}, color, lineWidth);
				drawLine(event, new Vector3f[] {vertices[i][0][j], vertices[i][1][j]}, color, lineWidth);
				drawLine(event, new Vector3f[] {vertices[i][j][0], vertices[i][j][1]}, color, lineWidth);
			}
		}
		
		for(int dir = 0; dir < 3; dir++) {
			for(int side = 0; side < 2; side++) {
				
				Vector3f[] arr = new Vector3f[4];
				
				boolean b = side == 1;
				
				if(dir != 1) b = !b;
				
				int index = b ? 3 : 0;
				int a = b ? -1 : 1;
				
				for(Vector2f v : RECT_VERTICES) {
					Vector3f vertex = null;
					
					int i = (int) v.x;
					int j = (int) v.y;
					
					if(dir == 0) vertex = vertices[side][i][j];
					else if(dir == 1) vertex = vertices[i][side][j];
					else if(dir == 2) vertex = vertices[i][j][side];
					
					arr[index] = vertex;
					
					index += a;
				}
				
				fillRect(event, arr, outlineColor);
			}
		}
	}
	
	public static void fillRect(RenderWorldLastEvent event, Vector3f[] vertices, Color color) {
		beginShape(event, GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		
		endShape(vertices, color);
	}
	
	public static void drawPath(RenderWorldLastEvent event, Path path, Color color, Color markerColor, float lineWidth) {
		int l = path.length();
		
		Vector3f[] vertices = new Vector3f[l];
		
		for(int i = 0; i < l; i++) {
			Node n = path.getNode(i);
			
			float x = n.getX() + 0.5f;
			float y = n.getY() + 0.5f;
			float z = n.getZ() + 0.5f;
			
			vertices[i] = new Vector3f(x, y, z);
		}
		
		drawLine(event, vertices, color, lineWidth);
		
		drawNode(event, path.getCurrentNode(), markerColor, lineWidth);
	}
	
	private static void drawNode(RenderWorldLastEvent event, Node n, Color color, float lineWidth) {
		float x = n.getX() + 0.5f;
		float y = n.getY();
		float z = n.getZ() + 0.5f;
		
		Vector3f[] vertices = new Vector3f[] {
				new Vector3f(x, y, z),
				new Vector3f(x, y + 1, z)
		};
		
		drawLine(event, vertices, color, lineWidth);
	}
	
	public static void drawLine(RenderWorldLastEvent event, Vector3f[] vertices, Color color, float width) {
		drawLine(event, vertices, color, width, true);
	}
	
	public static void drawLine(RenderWorldLastEvent event, Vector3f[] vertices, Color color, float width, boolean joint) {
		RenderSystem.lineWidth(width);
		
		int mode = joint ? GL11.GL_LINE_STRIP : GL11.GL_LINES;
		
		beginShape(event, mode, DefaultVertexFormats.POSITION_COLOR);
		
		endShape(vertices, color);
		
		RenderSystem.lineWidth(1);
	}
	
	private static void beginShape(RenderWorldLastEvent event, int mode, VertexFormat format) {
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		
		RenderSystem.enableDepthTest();
		
		RenderSystem.disableTexture();
		
		tessellator = Tessellator.getInstance();
		
		builder = tessellator.getBuilder();
		
		stack = event.getMatrixStack();
		
		Vector3d pos = GameContext.cameraPosition();
		
		stack.pushPose();
		stack.translate(-pos.x(), -pos.y(), -pos.z());
		
		matrix = stack.last().pose();
		
		builder.begin(mode, format);
	}
	
	private static void endShape(Vector3f[] vertices, Color color) {
		Vector4f colorVector = color.asVector();
		
		float r = colorVector.x();
		float g = colorVector.y();
		float b = colorVector.z();
		float a = colorVector.w();
		
		for(Vector3f v : vertices) {
			builder.vertex(matrix, v.x(), v.y(), v.z()).color(r, g, b, a).endVertex();
		}
		
		tessellator.end();
		
		stack.popPose();
		
		RenderSystem.enableTexture();
		
		RenderSystem.disableBlend();
	}
	
}
