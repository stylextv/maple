package de.stylextv.lynx.resource;

import de.stylextv.lynx.Constants;
import net.minecraft.util.ResourceLocation;

public class Texture {
	
	private ResourceLocation resource;
	
	public Texture(String path) {
		this.resource = new ResourceLocation(Constants.MOD_ID, path + ".png");
	}
	
	public ResourceLocation getResource() {
		return resource;
	}
	
}
