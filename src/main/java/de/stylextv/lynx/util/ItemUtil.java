package de.stylextv.lynx.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ItemUtil {
	
	public static ItemStack createStack(Item item, String title, String... lore) {
		return createStack(item, 1, title, lore);
	}
	
	public static ItemStack createStack(Item item, int amount, String title, String... lore) {
		ItemStack stack = new ItemStack(item, amount);
		
		CompoundNBT display = stack.getOrCreateTagElement("display");
		
		display.putString("Name", NBTUtil.toText(title));
		
		display.put("Lore", NBTUtil.toTextList(lore));
		
		return stack;
	}
	
}
