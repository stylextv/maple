package de.stylextv.maple.world.scan.block;

import java.util.function.Predicate;

import de.stylextv.maple.util.RegistryUtil;
import de.stylextv.maple.world.scan.block.filters.BlockTypeFilter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BlockFilter {
	
	public static final BlockFilter ALL = new BlockFilter((state) -> {
		return true;
	});
	
	private Predicate<BlockState> predicate;
	
	public BlockFilter() {
		this(null);
	}
	
	public BlockFilter(Predicate<BlockState> predicate) {
		this.predicate = predicate;
	}
	
	public boolean matches(BlockState state) {
		return predicate.test(state);
	}
	
	public static BlockFilter fromString(String s) {
		Block block = RegistryUtil.getBlock(s);
		
		if(block == null) return null;
		
		return new BlockTypeFilter(block);
	}
	
}
