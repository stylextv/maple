package de.stylextv.lynx.event.events;
import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class InventoryUpdateEvent extends Event {
	
	private DefaultedList<ItemStack> list;
	
	public InventoryUpdateEvent(DefaultedList<ItemStack> list) {
		this.list = list;
	}
	
	public DefaultedList<ItemStack> getList() {
		return list;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onInventoryUpdate(this);
	}
	
}
