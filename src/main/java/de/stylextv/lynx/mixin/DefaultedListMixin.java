package de.stylextv.lynx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin(DefaultedList.class)
public class DefaultedListMixin<T> {
	
	@Inject(method = "set(ILjava/lang/Objec;)V", at = @At("TAIL"))
	private void set(int index, T element, CallbackInfo info) {
		System.out.println("inv update");
		System.out.println(this);
		
//		EventBus.onEvent(new InventoryUpdateEvent());
	}
	
}
