package de.stylextv.dwarf.gui.menu.widget;

public class Size {
	
	public static final int ABSOLUTE = 0;
	public static final int PERCENT = 1;
	
	private int width;
	private int height;
	
	private int widthType;
	private int heightType;
	
	public Size() {
		this(0, 0);
	}
	
	public Size(int width, int height) {
		this(width, height, ABSOLUTE, ABSOLUTE);
	}
	
	public Size(int width, int height, int widthType, int heightType) {
		this.width = width;
		this.height = height;
		this.widthType = widthType;
		this.heightType = heightType;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidthType() {
		return widthType;
	}
	
	public int getHeightType() {
		return heightType;
	}
	
}
