package de.stylextv.lynx.gui.menu;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.gui.menu.widget.Alignment;
import de.stylextv.lynx.gui.menu.widget.Size;
import de.stylextv.lynx.gui.menu.widget.Widget;
import de.stylextv.lynx.input.PlayerContext;
import de.stylextv.lynx.resource.Texture;
import de.stylextv.lynx.scheme.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public abstract class Menu extends Screen {
	
	public static final int TEXT_HEIGHT = 7;
	
	private static final ITextComponent TITLE = new StringTextComponent(Constants.NAME + " Menu");
	
	private Widget root;
	
	public Menu() {
		super(TITLE);
		
		Alignment align = new Alignment(0, 0, Alignment.LEFT, Alignment.TOP);
		
		Size size = new Size(100, 100, Size.PERCENT, Size.PERCENT);
		
		root = new Widget(this, align, size);
		
		build();
	}
	
	public abstract void build();
	
	public void addWidget(Widget w) {
		root.addWidget(w);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		root.mouseClicked(mouseX, mouseY, button);
		
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float deltaTime) {
		renderBackground(stack);
		
		root.render(stack, mouseX, mouseY, deltaTime);
	}
	
	public void drawCenteredString(MatrixStack stack, String s, float x, float y, Color color) {
		drawString(stack, s, x - font.width(s) / 2f + 0.5f, y, color);
	}
	
	public void drawString(MatrixStack stack, String s, float x, float y, Color color) {
		font.draw(stack, s, x, y, color.asRGB());
	}
	
	public void drawRect(MatrixStack stack, int x, int y, int width, int height, Color color) {
		for(int i = 0; i < 2; i++) {
			fillRect(stack, x, y + height * i, width, 1, color);
			fillRect(stack, x + width * i, y, 1, height + 1, color);
		}
	}
	
	public void fillRect(MatrixStack stack, int x, int y, int width, int height, Color color) {
		int rgb = color.asRGB();
		
		fillGradient(stack, x, y, x + width, y + height, rgb, rgb);
	}
	
	public void drawTexture(MatrixStack stack, Texture t, int x, int y, int width, int height) {
		ResourceLocation resource = t.getResource();
		
		PlayerContext.minecraft().getTextureManager().bind(resource);
		
		blit(stack, x, y, 0, 0, width, height, width, height);
	}
	
	public Widget getRoot() {
		return root;
	}
	
}
