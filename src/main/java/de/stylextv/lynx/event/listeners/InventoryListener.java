package de.stylextv.lynx.event.listeners;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.event.EventListener;
import de.stylextv.lynx.event.events.InventoryUpdateEvent;
import de.stylextv.lynx.gui.ToolSet;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class InventoryListener implements EventListener {
	
	@Override
	public void onInventoryUpdate(InventoryUpdateEvent event) {
		DefaultedList<ItemStack> list = event.getList();
		
		PlayerInventory inv = PlayerContext.inventory();
		
		if(list.equals(inv.main)) ToolSet.updateTools();
	}
	
}
