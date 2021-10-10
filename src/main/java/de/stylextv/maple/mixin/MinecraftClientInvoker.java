package de.stylextv.maple.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public interface MinecraftClientInvoker {
	
	@Invoker("handleBlockBreaking")
	public void invokeHandleBlockBreaking(boolean b);
	
	@Invoker("doItemUse")
	public void invokeDoItemUse();
	
}
