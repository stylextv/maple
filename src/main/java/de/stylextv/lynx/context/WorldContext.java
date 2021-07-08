package de.stylextv.lynx.context;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class WorldContext {
	
	private static final Minecraft MC = Minecraft.getInstance();
	
	private static final int VIEW_DISTANCE = 12;
	
	public static ClientWorld world() {
		return MC.level;
	}
	
	public static String getLevelName() {
		String name = getWorldName() + "/" + getDimensionName();
		
		return name;
	}
	
	public static String getDimensionName() {
		RegistryKey<World> key = world().dimension();
		
		return key.location().getPath();
	}
	
	public static String getWorldName() {
		if(isInSinglePlayer()) {
			
			IntegratedServer server = MC.getSingleplayerServer();
			
			File f = server.getWorldScreenshotFile();
			
			f = f.getParentFile();
			
			return "local/" + f.getName();
			
		} else {
			
			ServerData server = MC.getCurrentServer();
			
			return "remote/" + server.ip;
		}
	}
	
	public static boolean isIngame() {
		if(!isInWorld()) return false;
		
		if(!isInSinglePlayer() || MC.getSingleplayerServer().isPublished()) return true;
		
		return !MC.isPaused();
	}
	
	public static boolean isInWorld() {
		return PlayerContext.player() != null && world() != null;
	}
	
	public static boolean isInSinglePlayer() {
		return MC.hasSingleplayerServer();
	}
	
	public static boolean isChunkInView(int cx, int cz) {
		if(!WorldContext.isIngame()) return false;
		
		BlockPos p = PlayerContext.player().blockPosition();
		
		ChunkPos pos = new ChunkPos(p);
		
		int dis = Math.abs(pos.x - cx) + Math.abs(pos.z - cz);
		
		return dis < VIEW_DISTANCE;
	}
	
	public static int getViewDistance() {
		return VIEW_DISTANCE;
	}
	
}
