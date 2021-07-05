package de.stylextv.dwarf.gui;

import de.stylextv.dwarf.gui.menu.MainMenu;
import de.stylextv.dwarf.gui.menu.Menu;
import de.stylextv.dwarf.input.PlayerContext;

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
