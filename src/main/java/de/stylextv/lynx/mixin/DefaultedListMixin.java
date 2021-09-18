package de.stylextv.lynx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.lynx.event.EventBus;
import de.stylextv.lynx.event.events.InventoryUpdateEvent;
import net.minecraft.util.collection.DefaultedList;

@Mixin(DefaultedList.class)
public class DefaultedListMixin {
	
	@Inject(method = "set(Ljava/lang/String;Z)V", at = @At("TAIL"))
	private void sendMessage(String message, boolean toHud, CallbackInfo info) {
		EventBus.onEvent(new InventoryUpdateEvent());
	}
	
}
