package de.stylextv.lynx.gui.hook;

import de.stylextv.lynx.gui.GuiManager;
import de.stylextv.lynx.input.PlayerContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotHook extends Slot {
	
	private ItemStack stack;
	
	public SlotHook(IInventory inv, int slot, ItemStack stack) {
		super(inv, slot, slot % 9 * 18 + 8, slot / 9 * 18 + 18);
		
		this.stack = stack;
		this.index = slot;
	}
	
	@Override
	public ItemStack onTake(PlayerEntity p, ItemStack stack) {
		PlayerContext.closeContainer();
		
		GuiManager.open();
		
		return stack;
	}
	
	@Override
	public ItemStack getItem() {
		return stack;
	}
	
}
