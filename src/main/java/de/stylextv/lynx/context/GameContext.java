package de.stylextv.lynx.context;

import java.io.File;

import de.stylextv.lynx.mixin.MinecraftClientInvoker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.math.Vec3d;

public class GameContext {
	
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	
	public static MinecraftClient client() {
		return CLIENT;
	}
	
	public static MinecraftClientInvoker clientInvoker() {
		return (MinecraftClientInvoker) CLIENT;
	}
	
	public static File directory() {
		return CLIENT.runDirectory;
	}
	
	public static GameOptions options() {
		return CLIENT.options;
	}
	
	public static ClientPlayerInteractionManager interactionManager() {
		return CLIENT.interactionManager;
	}
	
	public static ChatHud chatHud() {
		return inGameHud().getChatHud();
	}
	
	public static InGameHud inGameHud() {
		return CLIENT.inGameHud;
	}
	
	public static TextRenderer textRenderer() {
		return CLIENT.textRenderer;
	}
	
	public static float tickDelta() {
		return CLIENT.getTickDelta();
	}
	
	public static float lastFrameDuration() {
		return CLIENT.getLastFrameDuration() * 50;
	}
	
	public static Vec3d cameraPosition() {
		Camera camera = CLIENT.gameRenderer.getCamera();
		
		return camera.getPos();
	}
	
	public static boolean isIngame() {
		if(!WorldContext.isInWorld()) return false;
		
		if(!isInSinglePlayer() || isOpenToLan()) return true;
		
		return !CLIENT.isPaused();
	}
	
	public static boolean isOpenToLan() {
		IntegratedServer server = CLIENT.getServer();
		
		if(server == null) return false;
		
		return server.isRemote();
	}
	
	public static boolean isInSinglePlayer() {
		return CLIENT.isInSingleplayer();
	}
	
}
