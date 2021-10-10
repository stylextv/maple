package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class GuiController {
	
	public static void selectItem(ItemStack stack) {
		int slot = slotOf(stack);
		
		if(slot == -1) return;
		
		if(slot < 9) {
			
			selectSlot(slot);
			
			return;
		}
		
		moveToHotbar(slot);
	}
	
	public static void selectSlot(int slot) {
		PlayerInventory inv = PlayerContext.inventory();
		
		inv.selectedSlot = slot;
	}
	
	public static void moveToHotbar(int slot) {
		ClientPlayerInteractionManager interactionManager = GameContext.interactionManager();
		
		ClientPlayerEntity p = PlayerContext.player();
		
		PlayerInventory inv = PlayerContext.inventory();
		
		int syncId = p.playerScreenHandler.syncId;
		
		int moveTo = inv.selectedSlot;
		
		interactionManager.clickSlot(syncId, slot, moveTo, SlotActionType.SWAP, p);
	}
	
	public static int slotOf(ItemStack stack) {
		PlayerInventory inv = PlayerContext.inventory();
		
		for(int i = 0; i < 36; i++) {
			
			ItemStack other = inv.getStack(i);
			
			boolean equal = ItemStack.areEqual(stack, other);
			
			if(equal) return i;
		}
		
		return -1;
	}
	
}
