package de.stylextv.lynx.scheme;

import net.minecraft.util.math.vector.Vector4f;

public class Color {
	
	private int r;
	private int g;
	private int b;
	private int a;
	
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}
	
	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public int asRGB() {
		int rgb = a;
		rgb = (rgb << 8) + r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		
		return rgb;
	}
	
	public Vector4f asVector() {
		float x = r / 255f;
		float y = g / 255f;
		float z = b / 255f;
		float w = a / 255f;
		
		return new Vector4f(x, y, z, w);
	}
	
	public int getRed() {
		return r;
	}
	
	public int getGreen() {
		return g;
	}
	
	public int getBlue() {
		return b;
	}
	
	public int getAlpha() {
		return a;
	}
	
	public static Color blend(Color c1, Color c2, float f) {
		int r = blendChannels(c1.getRed(), c2.getRed(), f);
		int g = blendChannels(c1.getGreen(), c2.getGreen(), f);
		int b = blendChannels(c1.getBlue(), c2.getBlue(), f);
		int a = blendChannels(c1.getAlpha(), c2.getAlpha(), f);
		
		return new Color(r, g, b, a);
	}
	
	private static int blendChannels(int i1, int i2, float f) {
		return Math.round(i1 + (i2 - i1) * f);
	}
	
}
