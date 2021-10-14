package de.stylextv.maple.task.tasks;

import java.util.List;

import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;
import de.stylextv.maple.world.scan.block.BlockScanner;
import net.minecraft.util.math.BlockPos;

public class MineTask extends CompositeTask {
	
	private BlockFilter filter;
	
	public MineTask(BlockFilter filter) {
		this.filter = filter;
	}
	
	@Override
	public CompositeGoal refreshGoal() {
		List<BlockPos> positions = BlockScanner.scanWorld(filter);
		
		return CompositeGoal.fromPositions(positions);
	}
	
	@Override
	public void onFail() {
		ChatUtil.send("Can't get any closer to block.");
	}
	
	@Override
	public void onComplete() {
		ChatUtil.send("Can't find any matching blocks nearby.");
	}
	
	public BlockFilter getFilter() {
		return filter;
	}
	
}
