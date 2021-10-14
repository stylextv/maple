package de.stylextv.maple.util;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryUtil {
	
	public static Block getBlock(String id) {
		Identifier i = idFromString(id);
		
		Optional<Block> o = Registry.BLOCK.getOrEmpty(i);
		
		return o.orElse(null);
	}
	
	public static EntityType<?> getEntityType(String id) {
		Identifier i = idFromString(id);
		
		Optional<EntityType<?>> o = Registry.ENTITY_TYPE.getOrEmpty(i);
		
		return o.orElse(null);
	}
	
	private static Identifier idFromString(String s) {
		return Identifier.tryParse(s);
	}
	
}
