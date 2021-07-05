package de.stylextv.dwarf.gui.menu.widget;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.stylextv.dwarf.gui.menu.Menu;

public class Widget {
	
	private Menu menu;
	
	private Widget parent;
	
	private Alignment align;
	
	private Size size;
	
	private List<Widget> children;
	
	public Widget(Alignment align, Size size) {
		this(null, align, size);
	}
	
	public Widget(Menu menu, Alignment align, Size size) {
		this.menu = menu;
		
		this.align = align;
		this.size = size;
		
		this.children = new ArrayList<>();
	}
	
	public void setParent(Widget w) {
		parent = w;
		menu = w.getMenu();
	}
	
	public void addWidget(Widget w) {
		children.add(w);
		
		w.setParent(this);
	}
	
	public void mouseClicked(double mouseX, double mouseY, int button) {
		for(Widget w : children) {
			w.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	public void render(MatrixStack stack, int mouseX, int mouseY, float deltaTime) {
		for(Widget w : children) {
			w.render(stack, mouseX, mouseY, deltaTime);
		}
	}
	
	public boolean isMouseHovering(double mouseX, double mouseY) {
		int x = getX();
		int y = getY();
		
		int width = getWidth();
		int height = getHeight();
		
		if(mouseX < x || mouseY < y) return false;
		
		return mouseX - x < width && mouseY - y < height;
	}
	
	public int getX() {
		float f = align.getAlignX() / 2f;
		
		int x = Math.round(getParentX() + (getParentWidth() - getWidth()) * f);
		
		return x + align.getX();
	}
	
	public int getY() {
		float f = align.getAlignY() / 2f;
		
		int x = Math.round(getParentY() + (getParentHeight() - getHeight()) * f);
		
		return x + align.getY();
	}
	
	public int getWidth() {
		int w = size.getWidth();
		
		if(size.getWidthType() == Size.ABSOLUTE) return w;
		
		int parentWidth = getParentWidth();
		
		return Math.round(parentWidth / 100f * w);
	}
	
	public int getHeight() {
		int h = size.getHeight();
		
		if(size.getHeightType() == Size.ABSOLUTE) return h;
		
		int parentHeight = getParentHeight();
		
		return Math.round(parentHeight / 100f * h);
	}
	
	public int getParentX() {
		if(parent == null) return 0;
		
		return parent.getX();
	}
	
	public int getParentY() {
		if(parent == null) return 0;
		
		return parent.getY();
	}
	
	public int getParentWidth() {
		if(parent == null) return menu.width;
		
		return parent.getWidth();
	}
	
	public int getParentHeight() {
		if(parent == null) return menu.height;
		
		return parent.getHeight();
	}
	
	public Menu getMenu() {
		if(menu == null && parent != null) menu = parent.getMenu();
		
		return menu;
	}
	
	public Widget getParent() {
		return parent;
	}
	
}
