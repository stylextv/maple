package de.stylextv.lynx.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.stylextv.lynx.event.EventBus;
import de.stylextv.lynx.event.events.InventoryUpdateEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Mixin(DefaultedList.class)
public class DefaultedListMixin {
	
	@SuppressWarnings("unchecked")
	@Inject(method = "set(ILjava/lang/Object;)V", at = @At("TAIL"))
	private void set(int index, Object element, CallbackInfoReturnable<Object> info) {
		if(!(element instanceof ItemStack)) return;
		
		DefaultedList<ItemStack> list = (DefaultedList<ItemStack>)(Object) this;
		
		EventBus.onEvent(new InventoryUpdateEvent(list));
	}
	
}
