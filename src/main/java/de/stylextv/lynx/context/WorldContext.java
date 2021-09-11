package de.stylextv.lynx.context;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class WorldContext {
	
	private static final MinecraftClient MC = MinecraftClient.getInstance();
	
	private static final int VIEW_DISTANCE = 32;
	
	public static ClientWorld world() {
		return MC.world;
	}
	
	public static String getLevelName() {
		String name = getWorldName() + "/" + getDimensionName();
		
		return name;
	}
	
	public static String getDimensionName() {
		RegistryKey<World> key = world().getRegistryKey();
		
		Identifier identifier = key.getValue();
		
		return identifier.getNamespace();
	}
	
	public static String getWorldName() {
		if(GameContext.isInSinglePlayer()) {
			
			IntegratedServer server = MC.getServer();
			
			Optional<Path> optional = server.getIconFile();
			
			Path path = optional.get();
			
			File f = path.toFile();
			
			f = f.getParentFile();
			
			return "local/" + f.getName();
			
		} else {
			
			ServerInfo info = MC.getCurrentServerEntry();
			
			return "remote/" + info.address;
		}
	}
	
	public static boolean isInWorld() {
		return PlayerContext.player() != null && world() != null;
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
		return world().getBlockState(pos);
	}
	
	public static boolean isPosLoaded(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getZ();
		
		return isPosLoaded(x, y);
	}
	
	public static boolean isPosLoaded(int x, int z) {
		return world().isPosLoaded(x, z);
	}
	
}
