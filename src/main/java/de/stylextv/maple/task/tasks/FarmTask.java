package de.stylextv.maple.task.tasks;

import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;
import de.stylextv.maple.world.scan.block.BlockFilters;
import de.stylextv.maple.world.scan.block.filters.BlockTypeFilter;
import net.minecraft.block.Blocks;

public class FarmTask extends BreakTask {
	
	private static final BlockFilter FILTER = new BlockTypeFilter(
			Blocks.WHEAT,
			Blocks.CARROTS,
			Blocks.POTATOES,
			Blocks.BEETROOTS,
			Blocks.PUMPKIN,
			Blocks.MELON,
			Blocks.SUGAR_CANE,
			Blocks.BAMBOO,
			Blocks.CACTUS,
			Blocks.NETHER_WART
	);
	
	public FarmTask() {
		super(BlockFilters.fromFilters(FILTER, BlockFilter.FULLY_GROWN));
	}
	
	@Override
	public void onFail() {
		ChatUtil.send("Can't get any closer to crops.");
	}
	
	@Override
	public void onEmptyGoal() {
		ChatUtil.send("Can't find any matching crops nearby.");
	}
	
}
