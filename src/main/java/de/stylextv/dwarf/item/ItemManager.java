package de.stylextv.dwarf.item;

import de.stylextv.dwarf.Constants;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemManager {
	
	private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
	
	private static final RegistryObject<Item> ACCESSOR_ITEM = REGISTER.register("accessor", () -> new Item(new Item.Properties()));
	
	public static void register(IEventBus bus) {
		REGISTER.register(bus);
	}
	
	public static Item getAccessorItem() {
		return ACCESSOR_ITEM.get();
	}
	
}
