package de.stylextv.maple.context;

import net.minecraft.client.MinecraftClient;

public class ClientContext {
	
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	
	public static boolean isClientSide() {
		if(CLIENT.isOnThread()) return true;
		
		Thread thread = Thread.currentThread();
		
		String name = thread.getName().toLowerCase();
		
		return name.contains("client");
	}
	
}
