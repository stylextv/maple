package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class GuiController {
	
	public static void selectItem(ItemStack stack) {
		int slot = slotOf(stack);
		
		if(slot == -1) return;
		
		selectSlot(slot);
	}
	
	public static void selectSlot(int slot) {
		PlayerInventory inv = PlayerContext.inventory();
		
		inv.selectedSlot = slot;
	}
	
	public static int slotOf(ItemStack stack) {
		PlayerInventory inv = PlayerContext.inventory();
		
		for(int i = 0; i < 9; i++) {
			
			ItemStack other = inv.getStack(i);
			
			boolean equal = ItemStack.areEqual(stack, other);
			
			if(equal) return i;
		}
		
		return -1;
	}
	
}
