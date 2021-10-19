package de.stylextv.maple.render.mesh;

import java.util.HashMap;

import de.stylextv.maple.util.world.CoordUtil;
import net.minecraft.util.math.Vec3f;

public class BoxMesh extends Mesh {
	
	private static final HashMap<Long, BoxMesh> MESH_CACHE = new HashMap<>();
	
	private int width;
	private int height;
	private int length;
	
	public BoxMesh(int width, int height, int length) {
		this.width = width;
		this.height = height;
		this.length = length;
	}
	
	@Override
	public void create() {
		Vec3f[][][] map = new Vec3f[2][2][2];
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				for(int k = 0; k < 2; k++) {
					
					float x = width * i;
					float y = height * j;
					float z = length * k;
					
					map[i][j][k] = new Vec3f(x, y, z);
				}
			}
		}
		
		Vec3f[] vertices = new Vec3f[24];
		
		int index = 0;
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				
				vertices[index    ] = map[0][i][j];
				vertices[index + 1] = map[1][i][j];
				
				vertices[index + 2] = map[i][0][j];
				vertices[index + 3] = map[i][1][j];
				
				vertices[index + 4] = map[i][j][0];
				vertices[index + 5] = map[i][j][1];
				
				index += 6;
			}
		}
		
		setVertices(vertices);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getLength() {
		return length;
	}
	
	public static BoxMesh getMesh(int width, int height, int length) {
		long key = CoordUtil.posAsLong(width, height, length);
		
		BoxMesh mesh = MESH_CACHE.get(key);
		
		if(mesh == null) {
			
			mesh = new BoxMesh(width, height, length);
			
			MESH_CACHE.put(key, mesh);
		}
		
		return mesh;
	}
	
}
