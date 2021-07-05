package de.stylextv.lynx.gui.menu;

import de.stylextv.lynx.gui.menu.widget.Alignment;
import de.stylextv.lynx.gui.menu.widget.ModuleWidget;
import de.stylextv.lynx.gui.menu.widget.Size;
import de.stylextv.lynx.gui.menu.widget.TextWidget;
import de.stylextv.lynx.module.Module;
import de.stylextv.lynx.module.ModuleManager;
import de.stylextv.lynx.scheme.Colors;

public class MainMenu extends Menu {
	
	private static final int TITLE_SPACING = 38;
	private static final int CARD_SPACING = 25;
	
	@Override
	public void build() {
		int titleY = -ModuleWidget.HEIGHT / 2 - TEXT_HEIGHT - TITLE_SPACING;
		
		Alignment align = new Alignment(0, titleY, Alignment.CENTER, Alignment.CENTER);
		
		addWidget(new TextWidget("§nModules", Colors.TEXT, align, new Size()));
		
		Module[] modules = ModuleManager.getModules();
		
		int l = modules.length;
		
		for(int i = 0; i < l; i++) {
			
			Module m = modules[i];
			
			addCard(m, i - (l - 1) / 2f);
		}
	}
	
	private void addCard(Module m, float slot) {
		int x = Math.round((ModuleWidget.WIDTH + CARD_SPACING) * slot);
		
		Alignment align = new Alignment(x, 0, Alignment.CENTER, Alignment.CENTER);
		
		addWidget(new ModuleWidget(m, align));
	}
	
}
