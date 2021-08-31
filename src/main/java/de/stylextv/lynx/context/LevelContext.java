package de.stylextv.lynx.context;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;

public class LevelContext {
	
	private static final Minecraft MC = Minecraft.getInstance();
	
	private static final int VIEW_DISTANCE = 12;
	
	public static ClientLevel level() {
		return MC.level;
	}
	
	public static String getLevelName() {
		String name = getWorldName() + "/" + getDimensionName();
		
		return name;
	}
	
	public static String getDimensionName() {
		ResourceKey<Level> key = level().dimension();
		
		return key.location().getPath();
	}
	
	public static String getWorldName() {
		if(GameContext.isInSinglePlayer()) {
			
			IntegratedServer server = MC.getSingleplayerServer();
			
			Optional<Path> optional = server.getWorldScreenshotFile();
			
			Path path = optional.get();
			
			File f = path.toFile();
			
			f = f.getParentFile();
			
			return "local/" + f.getName();
			
		} else {
			
			ServerData server = MC.getCurrentServer();
			
			return "remote/" + server.ip;
		}
	}
	
	public static boolean isInLevel() {
		return PlayerContext.player() != null && level() != null;
	}
	
	public static boolean isChunkInView(int cx, int cz) {
		if(!GameContext.isIngame()) return false;
		
		ChunkPos pos = PlayerContext.chunkPosition();
		
		int dis = Math.abs(pos.x - cx) + Math.abs(pos.z - cz);
		
		return dis < VIEW_DISTANCE;
	}
	
	public static int getViewDistance() {
		return VIEW_DISTANCE;
	}
	
	public static Block getBlock(BlockPos pos) {
		BlockState state = getBlockState(pos);
		
		return state.getBlock();
	}
	
	public static BlockState getBlockState(BlockPos pos) {
		return level().getBlockState(pos);
	}
	
	public static boolean isBlockLoaded(BlockPos pos) {
		return level().isLoaded(pos);
	}
	
	public static boolean isInsideBorder(BlockPos pos) {
		WorldBorder border = level().getWorldBorder();
		
		return border.isWithinBounds(pos);
	}
	
}
