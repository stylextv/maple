package de.stylextv.maple.schematic;

import net.minecraft.block.BlockState;

public abstract class Schematic {
	
	private int width;
	private int height;
	private int length;
	
	public Schematic(int width, int height, int length) {
		this.width = width;
		this.height = height;
		this.length = length;
	}
	
	public abstract boolean matches(int x, int y, int z, BlockState state);
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getLength() {
		return length;
	}
	
}
