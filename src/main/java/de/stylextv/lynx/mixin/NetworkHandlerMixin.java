package de.stylextv.lynx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.lynx.event.EventBus;
import de.stylextv.lynx.event.events.BlockUpdateEvent;
import de.stylextv.lynx.event.events.ChunkEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.util.math.BlockPos;

@Mixin(ClientPlayNetworkHandler.class)
public class NetworkHandlerMixin {
	
	@Inject(method = "onChunkData(Lnet/minecraft/network/packet/s2c/play/ChunkDataS2CPacket;)V", at = @At("TAIL"))
	private void onChunkData(ChunkDataS2CPacket packet, CallbackInfo info) {
		int x = packet.getX();
		int z = packet.getZ();
		
		EventBus.onEvent(new ChunkEvent(ChunkEvent.Type.DATA, x, z));
	}
	
	@Inject(method = "onBlockUpdate(Lnet/minecraft/network/packet/s2c/play/BlockUpdateS2CPacket;)V", at = @At("TAIL"))
	private void onBlockUpdate(BlockUpdateS2CPacket packet, CallbackInfo info) {
		BlockPos pos = packet.getPos();
		
		EventBus.onEvent(new BlockUpdateEvent(pos));
	}
	
}
