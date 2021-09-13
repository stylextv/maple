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
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class WorldContext {
	
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	
	public static ClientWorld world() {
		return CLIENT.world;
	}
	
	public static String getLevelName() {
		String name = getWorldName() + "/" + getDimensionName();
		
		return name;
	}
	
	public static String getDimensionName() {
		RegistryKey<World> key = world().getRegistryKey();
		
		Identifier identifier = key.getValue();
		
		return identifier.getPath();
	}
	
	public static String getWorldName() {
		if(GameContext.isInSinglePlayer()) {
			
			IntegratedServer server = CLIENT.getServer();
			
			Optional<Path> optional = server.getIconFile();
			
			Path path = optional.get();
			
			File f = path.toFile();
			
			f = f.getParentFile();
			
			return "local/" + f.getName();
			
		} else {
			
			ServerInfo info = CLIENT.getCurrentServerEntry();
			
			return "remote/" + info.address;
		}
	}
	
	public static boolean isInWorld() {
		return PlayerContext.player() != null && world() != null;
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
