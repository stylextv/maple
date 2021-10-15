package de.stylextv.maple.task.tasks;

import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.pathing.calc.goal.GetToBlockGoal;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;
import de.stylextv.maple.world.scan.block.BlockFilters;

public class GetToBlockTask extends ScanTask {
	
	private CompositeGoal goal;
	
	public GetToBlockTask(BlockFilter filter) {
		this(BlockFilters.fromFilter(filter));
	}
	
	public GetToBlockTask(BlockFilters filters) {
		super(filters);
	}
	
	@Override
	public CompositeGoal onTick() {
		if(hasScanExpired() && !isScanning()) {
			
			rescan((positions) -> {
				
				goal = CompositeGoal.fromCollection(positions, pos -> new GetToBlockGoal(pos));
				
			});
		}
		
		return goal;
	}
	
	@Override
	public void onFail() {
		ChatUtil.send("Can't get any closer to block.");
	}
	
	@Override
	public void onComplete() {
		ChatUtil.send("Can't find any matching blocks nearby.");
	}
	
}
