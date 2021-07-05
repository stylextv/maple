package de.stylextv.dwarf.gui.menu.widget;

import de.stylextv.dwarf.module.Module;
import de.stylextv.dwarf.scheme.Colors;

public class ModuleWidget extends Widget {
	
	private static final int SPACING = 6;
	
	private static final int BUTTON_HEIGHT = 23;
	
	public static final int WIDTH = 130;
	public static final int HEIGHT = WIDTH + (SPACING + BUTTON_HEIGHT) * 2;
	
	private Module module;
	
	public ModuleWidget(Module module, Alignment align) {
		super(align, new Size(WIDTH, HEIGHT));
		
		this.module = module;
		
		build();
	}
	
	private void build() {
		addTitle();
		addTexture();
		addButtons();
	}
	
	private void addTitle() {
		Alignment align = new Alignment(0, 0, Alignment.CENTER, Alignment.TOP);
		
		Size size = new Size(WIDTH, BUTTON_HEIGHT);
		
		addWidget(new TextWidget(module.getName(), Colors.TEXT, true, align, size));
	}
	
	private void addTexture() {
		Alignment align = new Alignment(0, BUTTON_HEIGHT + SPACING, Alignment.CENTER, Alignment.TOP);
		
		Size size = new Size(WIDTH, WIDTH);
		
		addWidget(new TextureWidget(module.getIcon(), align, size));
	}
	
	private void addButtons() {
		int y = WIDTH + SPACING + SPACING + BUTTON_HEIGHT;
		
		int width = WIDTH - SPACING - BUTTON_HEIGHT;
		
		addToggleButton(0, y, width, BUTTON_HEIGHT);
		addSettingsButton(width + SPACING, y, BUTTON_HEIGHT, BUTTON_HEIGHT);
	}
	
	private void addToggleButton(int x, int y, int width, int height) {
		Alignment align = new Alignment(x, y, Alignment.LEFT, Alignment.TOP);
		
		Size size = new Size(width, height);
		
		addWidget(new Button("Start", align, size));
	}
	
	private void addSettingsButton(int x, int y, int width, int height) {
		Alignment align = new Alignment(x, y, Alignment.LEFT, Alignment.TOP);
		
		Size size = new Size(width, height);
		
		addWidget(new Button("S", align, size));
	}
	
}
