package de.stylextv.maple.world.scan.block;

import de.stylextv.maple.world.scan.ScanFilters;
import net.minecraft.block.BlockState;

public class BlockFilters extends ScanFilters<BlockFilter, BlockState> {
	
	public BlockFilters(BlockFilter... filters) {
		super(filters);
	}
	
	public static BlockFilters fromFilter(BlockFilter filter) {
		return new BlockFilters(filter);
	}
	
	public static BlockFilters fromFilters(BlockFilter... filters) {
		return new BlockFilters(filters);
	}
	
}
