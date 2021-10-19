package de.stylextv.maple.render.mesh;

import net.minecraft.util.math.Vec3f;

public abstract class Mesh {
	
	private Vec3f[] vertices;
	
	public abstract void create();
	
	public Vec3f[] getVertices() {
		if(vertices == null) create();
		
		return vertices;
	}
	
	public void setVertices(Vec3f[] vertices) {
		this.vertices = vertices;
	}
	
}
