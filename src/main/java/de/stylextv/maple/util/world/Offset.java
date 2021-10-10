package de.stylextv.maple.util.world;

public class Offset {
	
	public static final Offset[] TRIPLED_BLOCK_CORNERS = {
			new Offset(  0, 0.1, 0.1),
			new Offset(  0, 0.9, 0.1),
			new Offset(  0, 0.1, 0.9),
			new Offset(  0, 0.9, 0.9),
			new Offset(  1, 0.1, 0.1),
			new Offset(  1, 0.9, 0.1),
			new Offset(  1, 0.1, 0.9),
			new Offset(  1, 0.9, 0.9),
			
			new Offset(0.1,   0, 0.1),
			new Offset(0.9,   0, 0.1),
			new Offset(0.1,   0, 0.9),
			new Offset(0.9,   0, 0.9),
			new Offset(0.1,   1, 0.1),
			new Offset(0.9,   1, 0.1),
			new Offset(0.1,   1, 0.9),
			new Offset(0.9,   1, 0.9),
			
			new Offset(0.1, 0.1,   0),
			new Offset(0.9, 0.1,   0),
			new Offset(0.1, 0.9,   0),
			new Offset(0.9, 0.9,   0),
			new Offset(0.1, 0.1,   1),
			new Offset(0.9, 0.1,   1),
			new Offset(0.1, 0.9,   1),
			new Offset(0.9, 0.9,   1),
	};
	
	public static final Offset[] BLOCK_CORNERS = {
			new Offset(  0,   0,   0),
			new Offset(  1,   0,   0),
			new Offset(  0,   0,   1),
			new Offset(  1,   0,   1),
			
			new Offset(  0,   1,   0),
			new Offset(  1,   1,   0),
			new Offset(  0,   1,   1),
			new Offset(  1,   1,   1)
	};
	
	public static final Offset[] BLOCK_NEIGHBOURS = {
			new Offset(  0,  -1,   0),
			new Offset(  1,  -1,   0),
			new Offset( -1,  -1,   0),
			new Offset(  0,  -1,   1),
			new Offset(  0,  -1,  -1),
			new Offset(  1,  -1,   1),
			new Offset( -1,  -1,   1),
			new Offset(  1,  -1,  -1),
			new Offset( -1,  -1,  -1),
			
			new Offset(  1,   0,   0),
			new Offset( -1,   0,   0),
			new Offset(  0,   0,   1),
			new Offset(  0,   0,  -1),
			new Offset(  1,   0,   1),
			new Offset( -1,   0,   1),
			new Offset(  1,   0,  -1),
			new Offset( -1,   0,  -1),
			
			new Offset(  0,   1,   0),
			new Offset(  1,   1,   0),
			new Offset( -1,   1,   0),
			new Offset(  0,   1,   1),
			new Offset(  0,   1,  -1),
			new Offset(  1,   1,   1),
			new Offset( -1,   1,   1),
			new Offset(  1,   1,  -1),
			new Offset( -1,   1,  -1),
	};
	
	public static final Offset[] DIRECT_BLOCK_NEIGHBOURS = {
			new Offset(  1,   0,   0),
			new Offset( -1,   0,   0),
			new Offset(  0,   1,   0),
			new Offset(  0,  -1,   0),
			new Offset(  0,   0,   1),
			new Offset(  0,   0,  -1)
	};
	
	public static final Offset[] UPPER_DIRECT_BLOCK_NEIGHBOURS = {
			new Offset(  1,   0,   0),
			new Offset( -1,   0,   0),
			new Offset(  0,   1,   0),
			new Offset(  0,   0,   1),
			new Offset(  0,   0,  -1)
	};
	
	private double x;
	private double y;
	private double z;
	
	private int blockX;
	private int blockY;
	private int blockZ;
	
	public Offset() {
		this(0, 0, 0);
	}
	
	public Offset(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.blockX = CoordUtil.unitToBlockPos(x);
		this.blockY = CoordUtil.unitToBlockPos(y);
		this.blockZ = CoordUtil.unitToBlockPos(z);
	}
	
	public Offset add(Offset o) {
		return add(o.getX(), o.getY(), o.getZ());
	}
	
	public Offset add(double x, double y, double z) {
		double newX = this.x + x;
		double newY = this.y + y;
		double newZ = this.z + z;
		
		return new Offset(newX, newY, newZ);
	}
	
	public Offset subtract(Offset o) {
		return subtract(o.getX(), o.getY(), o.getZ());
	}
	
	public Offset subtract(double x, double y, double z) {
		return add(-x, -y, -z);
	}
	
	public Offset multiply(double d) {
		return multiply(d, d, d);
	}
	
	public Offset multiply(Offset o) {
		return multiply(o.getX(), o.getY(), o.getZ());
	}
	
	public Offset multiply(double x, double y, double z) {
		double newX = this.x * x;
		double newY = this.y * y;
		double newZ = this.z * z;
		
		return new Offset(newX, newY, newZ);
	}
	
	public Offset divide(double d) {
		return divide(d, d, d);
	}
	
	public Offset divide(Offset o) {
		return divide(o.getX(), o.getY(), o.getZ());
	}
	
	public Offset divide(double x, double y, double z) {
		x = 1 / x;
		y = 1 / y;
		z = 1 / z;
		
		return multiply(x, y, z);
	}
	
	public int getBlockX() {
		return blockX;
	}
	
	public int getBlockY() {
		return blockY;
	}
	
	public int getBlockZ() {
		return blockZ;
	}
	
	@Override
	public String toString() {
		return String.format("Offset{x=%s, y=%s, z=%s}", x, y, z);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
}
