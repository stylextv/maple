package de.stylextv.lynx.gui.menu.widget;

public class Alignment {
	
	public static final int LEFT = 0;
	public static final int TOP = 0;
	
	public static final int RIGHT = 2;
	public static final int BOTTOM = 2;
	
	public static final int CENTER = 1;
	
	private int x;
	private int y;
	
	private int alignX;
	private int alignY;
	
	public Alignment(int alignX, int alignY) {
		this(0, 0, alignX, alignY);
	}
	
	public Alignment(int x, int y, int alignX, int alignY) {
		this.x = x;
		this.y = y;
		this.alignX = alignX;
		this.alignY = alignY;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getAlignX() {
		return alignX;
	}
	
	public int getAlignY() {
		return alignY;
	}
	
}
