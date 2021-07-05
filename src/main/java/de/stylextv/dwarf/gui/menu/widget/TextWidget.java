package de.stylextv.dwarf.gui.menu.widget;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.stylextv.dwarf.gui.menu.Menu;
import de.stylextv.dwarf.scheme.Color;
import de.stylextv.dwarf.scheme.Colors;

public class TextWidget extends Widget {
	
	private String text;
	
	private Color color;
	
	private boolean background;
	
	public TextWidget(String text, Color color, Alignment align, Size size) {
		this(text, color, false, align, size);
	}
	
	public TextWidget(String text, Color color, boolean background, Alignment align, Size size) {
		super(align, size);
		
		this.text = text;
		this.color = color;
		this.background = background;
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float deltaTime) {
		Menu m = getMenu();
		
		int x = getX();
		int y = getY();
		
		int width = getWidth();
		int height = getHeight();
		
		float textX = x + width / 2f;
		float textY = y + height / 2f - Menu.TEXT_HEIGHT / 2f;
		
		if(background) {
			m.fillRect(stack, x, y, width, height, Colors.BUTTON_IDLE);
		}
		
		m.drawCenteredString(stack, text, textX, textY, color);
		
		super.render(stack, mouseX, mouseY, deltaTime);
	}
	
}
