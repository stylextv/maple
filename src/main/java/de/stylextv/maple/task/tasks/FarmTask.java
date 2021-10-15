package de.stylextv.maple.task.tasks;

import java.util.List;

import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;
import de.stylextv.maple.world.scan.block.BlockScanner;
import de.stylextv.maple.world.scan.block.filters.BlockTypeFilter;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class FarmTask extends CompositeTask {
	
	private static final BlockFilter FILTER = new BlockTypeFilter(
			Blocks.WHEAT,
			Blocks.CARROTS,
			Blocks.POTATOES,
			Blocks.BEETROOTS,
			Blocks.PUMPKIN,
			Blocks.MELON,
			Blocks.SUGAR_CANE,
			Blocks.CACTUS,
			Blocks.NETHER_WART
	);
	
	@Override
	public CompositeGoal refreshGoal() {
		List<BlockPos> positions = BlockScanner.scanWorld(FILTER, BlockFilter.FULLY_GROWN);
		
		return CompositeGoal.fromPositions(positions);
	}
	
	@Override
	public void onFail() {
		ChatUtil.send("Can't get any closer to crop.");
	}
	
	@Override
	public void onComplete() {
		ChatUtil.send("Can't find any matching crops nearby.");
	}
	
}
