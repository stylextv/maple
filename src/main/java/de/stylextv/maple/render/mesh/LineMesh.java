package de.stylextv.maple.render.mesh;

import net.minecraft.util.math.Vec3f;

public class LineMesh extends Mesh {
	
	private static final LineMesh[][][] MESHES = new LineMesh[3][3][3];
	
	static {
		for(int dx = -1; dx <= 1; dx++) {
			for(int dy = -1; dy <= 1; dy++) {
				for(int dz = -1; dz <= 1; dz++) {
					
					LineMesh mesh = new LineMesh(dx, dy, dz);
					
					int i = dx + 1;
					int j = dy + 1;
					int k = dz + 1;
					
					MESHES[i][j][k] = mesh;
				}
			}
		}
	}
	
	private float dx;
	private float dy;
	private float dz;
	
	public LineMesh(float dx, float dy, float dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	@Override
	public void create() {
		Vec3f[] vertices = new Vec3f[2];
		
		vertices[0] = Vec3f.ZERO;
		vertices[1] = new Vec3f(dx, dy, dz);
		
		setVertices(vertices);
	}
	
	public float getDeltaX() {
		return dx;
	}
	
	public float getDeltaY() {
		return dy;
	}
	
	public float getDeltaZ() {
		return dz;
	}
	
	public static LineMesh getMesh(int dx, int dy, int dz) {
		return MESHES[dx + 1][dy + 1][dz + 1];
	}
	
}
