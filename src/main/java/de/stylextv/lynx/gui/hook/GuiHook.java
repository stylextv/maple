package de.stylextv.lynx.gui.hook;

import java.util.List;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.item.ItemManager;
import de.stylextv.lynx.util.ItemUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class GuiHook {
	
	private static final String MENU_TITLE = "SkyBlock Menu";
	
	private static final int ACCESSOR_SLOT = 16;
	
	public static boolean isMenuGui(Screen screen) {
		if(!(screen instanceof ChestScreen)) return false;
		
		ChestScreen chestScreen = (ChestScreen) screen;
		
		ITextComponent title = chestScreen.getTitle();
		
		return title.getString().equals(MENU_TITLE);
	}
	
	public static void installAccessor(Screen screen) {
		ChestScreen chestScreen = (ChestScreen) screen;
		
		ChestContainer container = chestScreen.getMenu();
		
		List<Slot> list = container.slots;
		
		int slot = ACCESSOR_SLOT;
		
		SlotHook hook = new SlotHook(container.getContainer(), slot, createItem());
		
		list.add(slot, hook);
		list.remove(slot + 1);
	}
	
	private static ItemStack createItem() {
		String title = Constants.COLORED_NAME;
		
		String[] lore = new String[] {
				"§8" + Constants.VERSION,
				"",
				"§7Opens a menu from which",
				"§7you can run and configure",
				"§7the Dwarf bot.",
				"",
				"§eClick to open!"
		};
		
		ItemStack stack = ItemUtil.createStack(ItemManager.getAccessorItem(), title, lore);
		
		return stack;
	}
	
	public static int getAccessorSlot() {
		return ACCESSOR_SLOT;
	}
	
}
