package de.stylextv.lynx.util;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

public class ItemUtil {
	
	public static float getToolScore(ItemStack stack, BlockState state) {
		float score = getDigSpeed(stack, state);
		
		if(stack.isDamageable() && score <= 1) {
			
			score = -stack.getRepairCost() - 1;
		}
		
		return score;
	}
	
	public static float getDigSpeed(ItemStack stack, BlockState state) {
		float f = stack.getMiningSpeedMultiplier(state);
		
		if(f <= 1) return f;
		
		int level = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
		
		if(level > 0) f += level * level + 1;
		
		return f;
	}
	
}
