package de.stylextv.maple.render.mesh;

import java.util.HashMap;

import de.stylextv.maple.util.world.CoordUtil;
import net.minecraft.util.math.Vec3f;

public class LineMesh extends Mesh {
	
	private static final HashMap<Long, LineMesh> MESH_CACHE = new HashMap<>();
	
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
		long key = CoordUtil.posAsLong(dx, dy, dz);
		
		LineMesh mesh = MESH_CACHE.get(key);
		
		if(mesh == null) {
			
			mesh = new LineMesh(dx, dy, dz);
			
			MESH_CACHE.put(key, mesh);
		}
		
		return mesh;
	}
	
}
