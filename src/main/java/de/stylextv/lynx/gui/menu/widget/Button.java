package de.stylextv.lynx.gui.menu.widget;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.stylextv.lynx.gui.menu.Menu;
import de.stylextv.lynx.input.PlayerContext;
import de.stylextv.lynx.input.controller.MovementController;
import de.stylextv.lynx.pathing.Path;
import de.stylextv.lynx.pathing.PathFinder;
import de.stylextv.lynx.pathing.goal.YGoal;
import de.stylextv.lynx.scheme.Color;
import de.stylextv.lynx.scheme.Colors;
import de.stylextv.lynx.util.MathUtil;

public class Button extends Widget {
	
	private String text;
	
	private float state;
	
	public Button(String text, Alignment align, Size size) {
		super(align, size);
		
		this.text = text;
	}
	
	private void update(int mouseX, int mouseY, float deltaTime) {
		boolean b = isMouseHovering(mouseX, mouseY);
		
		int i = b ? 1 : -1;
		
		state += 0.5f * i * deltaTime;
		
		state = MathUtil.clamp(state, 0, 1);
	}
	
	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if(button != 0) return;
		
		if(isMouseHovering(mouseX, mouseY)) {
			PathFinder finder = new PathFinder(new YGoal(25));
			
			Path path = finder.find(PlayerContext.blockPosition());
			
			MovementController.followPath(path);
		}
		
		super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float deltaTime) {
		update(mouseX, mouseY, deltaTime);
		
		Menu m = getMenu();
		
		int x = getX();
		int y = getY();
		
		Color color = Color.blend(Colors.BUTTON_IDLE, Colors.BUTTON_HOVER, state);
		Color textColor = Color.blend(Colors.BUTTON_TEXT_IDLE, Colors.BUTTON_TEXT_HOVER, state);
		
		int width = getWidth();
		int height = getHeight();
		
		m.fillRect(stack, x, y, width, height, color);
		
		float textX = x + width / 2f;
		float textY = y + height / 2f - Menu.TEXT_HEIGHT / 2f;
		
		m.drawCenteredString(stack, text, textX, textY, textColor);
		
		super.render(stack, mouseX, mouseY, deltaTime);
	}
	
}
