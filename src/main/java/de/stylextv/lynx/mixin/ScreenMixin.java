package de.stylextv.lynx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.lynx.event.EventBus;
import de.stylextv.lynx.event.events.ClientChatEvent;
import net.minecraft.client.gui.screen.Screen;

@Mixin(Screen.class)
public class ScreenMixin {
	
	@Inject(method = "sendMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
	private void sendMessage(String message, boolean toHud, CallbackInfo info) {
		ClientChatEvent event = new ClientChatEvent(message);
		
		EventBus.onEvent(event);
		
		if(event.isCanceled()) info.cancel();
	}
	
}
