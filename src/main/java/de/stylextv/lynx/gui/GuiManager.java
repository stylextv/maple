package de.stylextv.lynx.gui;

import de.stylextv.lynx.gui.menu.MainMenu;
import de.stylextv.lynx.gui.menu.Menu;
import de.stylextv.lynx.input.PlayerContext;

public class GuiManager {
	
	public static void open() {
		openMenu(new MainMenu());
	}
	
	public static void close() {
		openMenu(null);
	}
	
	public static void openMenu(Menu m) {
		PlayerContext.minecraft().setScreen(m);
	}
	
}
