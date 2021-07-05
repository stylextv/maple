package de.stylextv.lynx.gui.menu.widget;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.stylextv.lynx.gui.menu.Menu;
import de.stylextv.lynx.resource.Texture;
import de.stylextv.lynx.scheme.Colors;

public class TextureWidget extends Widget {
	
	private static final int MARGIN = 22;
	
	private Texture texture;
	
	public TextureWidget(Texture texture, Alignment align, Size size) {
		super(align, size);
		
		this.texture = texture;
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float deltaTime) {
		Menu m = getMenu();
		
		int x = getX();
		int y = getY();
		
		int width = getWidth();
		int height = getHeight();
		
		m.fillRect(stack, x, y, width, height, Colors.BUTTON_IDLE);
		
		int texX = x + MARGIN;
		int texY = y + MARGIN;
		
		int texWidth = width - MARGIN * 2;
		int texHeight = height - MARGIN * 2;
		
		m.drawTexture(stack, texture, texX, texY, texWidth, texHeight);
		
		super.render(stack, mouseX, mouseY, deltaTime);
	}
	
}
