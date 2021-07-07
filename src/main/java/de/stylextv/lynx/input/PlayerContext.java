package de.stylextv.lynx.input;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class PlayerContext {
	
	private static final Minecraft MC = Minecraft.getInstance();
	
	public static Minecraft minecraft() {
		return MC;
	}
	
	public static float deltaTime() {
		return MC.getDeltaFrameTime();
	}
	
	public static ClientPlayerEntity player() {
		return MC.player;
	}
	
	public static Vector3d position() {
		return player().position();
	}
	
	public static BlockPos blockPosition() {
		return player().blockPosition();
	}
	
	public static BlockPos feetPosition() {
		return new BlockPos(position().add(0, 0.125f, 0));
	}
	
	public static double horizontalSpeed() {
		Vector3d v = MC.player.getDeltaMovement();
		
		return v.multiply(1, 0, 1).length();
	}
	
	public static double verticalSpeed() {
		Vector3d v = MC.player.getDeltaMovement();
		
		return v.y();
	}
	
	public static double speed() {
		return MC.player.getDeltaMovement().length();
	}
	
	public static boolean isOnGround() {
		return MC.player.isOnGround();
	}
	
	public static ClientWorld world() {
		return MC.level;
	}
	
	public static PlayerController controller() {
		return MC.gameMode;
	}
	
	public static RayTraceResult objectUnderCrosshair() {
		return MC.hitResult;
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
	
	public static GameSettings gameSettings() {
		return MC.options;
	}
	
	public static Vector3d cameraPosition() {
		return MC.gameRenderer.getMainCamera().getPosition();
	}
	
	public static boolean isIngame() {
		if(!isInWorld()) return false;
		
		if(!MC.hasSingleplayerServer() || MC.getSingleplayerServer().isPublished()) return true;
		
		return !MC.isPaused();
	}
	
	public static boolean isInWorld() {
		return player() != null && world() != null;
	}
	
	public static void closeContainer() {
		player().closeContainer();
	}
	
	public static boolean setFlying(boolean b) {
		return MC.player.abilities.flying = b;
	}
	
}
