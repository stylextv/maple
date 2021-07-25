package de.stylextv.lynx.context;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.phys.Vec3;

public class GameContext {
	
	private static final Minecraft MC = Minecraft.getInstance();
	
	public static Minecraft minecraft() {
		return MC;
	}
	
	public static File directory() {
		return MC.gameDirectory;
	}
	
	public static Options settings() {
		return MC.options;
	}
	
	public static ChatComponent chat() {
		return MC.gui.getChat();
	}
	
	public static Screen screen() {
		return MC.screen;
	}
	
	public static Font font() {
		return MC.font;
	}
	
	public static float deltaTime() {
		return MC.getDeltaFrameTime();
	}
	
	public static Vec3 cameraPosition() {
		return MC.gameRenderer.getMainCamera().getPosition();
	}
	
	public static boolean isIngame() {
		if(!LevelContext.isInLevel()) return false;
		
		if(!isInSinglePlayer() || MC.getSingleplayerServer().isPublished()) return true;
		
		return !MC.isPaused();
	}
	
	public static boolean isInSinglePlayer() {
		return MC.hasSingleplayerServer();
	}
	
}
