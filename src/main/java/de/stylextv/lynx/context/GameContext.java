package de.stylextv.lynx.context;

import java.io.File;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.math.Vec3d;

public class GameContext {
	
	private static final MinecraftClient MC = MinecraftClient.getInstance();
	
	public static MinecraftClient minecraft() {
		return MC;
	}
	
	public static File directory() {
		return MC.runDirectory;
	}
	
	public static GameOptions options() {
		return MC.options;
	}
	
	public static ChatHud chatHud() {
		return inGameHud().getChatHud();
	}
	
	public static InGameHud inGameHud() {
		return MC.inGameHud;
	}
	
	public static TextRenderer textRenderer() {
		return MC.textRenderer;
	}
	
	public static float tickDelta() {
		return MC.getTickDelta();
	}
	
	public static Vec3d cameraPosition() {
		Camera camera = MC.gameRenderer.getCamera();
		
		return camera.getPos();
	}
	
	public static boolean isIngame() {
		if(!WorldContext.isInWorld()) return false;
		
		if(!isInSinglePlayer() || isOpenToLan()) return true;
		
		return !MC.isPaused();
	}
	
	public static boolean isOpenToLan() {
		IntegratedServer server = MC.getServer();
		
		if(server == null) return false;
		
		return server.isRemote();
	}
	
	public static boolean isInSinglePlayer() {
		return MC.isInSingleplayer();
	}
	
}
