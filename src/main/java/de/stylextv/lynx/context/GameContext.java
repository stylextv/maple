package de.stylextv.lynx.context;

import java.io.File;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.Screen;

public class GameContext {
	
	private static final Minecraft MC = Minecraft.getInstance();
	
	public static Minecraft minecraft() {
		return MC;
	}
	
	public static File directory() {
		return MC.gameDirectory;
	}
	
	public static GameSettings settings() {
		return MC.options;
	}
	
	public static IngameGui ingameGui() {
		return MC.gui;
	}
	
	public static Screen screen() {
		return MC.screen;
	}
	
	public static FontRenderer font() {
		return MC.font;
	}
	
	public static float deltaTime() {
		return MC.getDeltaFrameTime();
	}
	
}
