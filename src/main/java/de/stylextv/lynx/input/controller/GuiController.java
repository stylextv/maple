package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiController {
	
	public static ItemStack bestTool(BlockState state) {
		PlayerInventory inv = PlayerContext.inventory();
		
		ItemStack best = null;
		
		float score = Float.NEGATIVE_INFINITY;
		
		for(int i = 0; i < 9; i++) {
			
			ItemStack stack = inv.getStack(i);
			
			float f = digSpeed(stack, state);
			
			if(stack.isDamageable() && f <= 1) {
				
				f = -stack.getRepairCost() - 1;
			}
			
			if(f > score) {
				
				best = stack;
				score = f;
			}
		}
		
		return best;
	}
	
	public static float digSpeed(ItemStack stack, BlockState state) {
		float f = stack.getMiningSpeedMultiplier(state);
		
		if(f <= 1) return f;
		
		int level = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
		
		if(level > 0) f += level * level + 1;
		
		return f;
	}
	
	// TODO check for full blocks only (or maybe just DIRT, STONE, ...)
	public static ItemStack buildingMaterial() {
		PlayerInventory inv = PlayerContext.inventory();
		
		for(int i = 0; i < 9; i++) {
			
			ItemStack stack = inv.getStack(i);
			
			Item item = stack.getItem();
			
			if(item instanceof BlockItem) return stack;
		}
		
		return null;
	}
	
	public static boolean hasBuildingMaterial() {
		return buildingMaterial() != null;
	}
	
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
			
			if(stack.isItemEqual(other)) return i;
		}
		
		return -1;
	}
	
}
