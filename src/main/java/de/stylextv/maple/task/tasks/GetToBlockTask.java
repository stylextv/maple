package de.stylextv.maple.task.tasks;

import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.pathing.calc.goal.GetToBlockGoal;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;

public class GetToBlockTask extends ScanTask {
	
	private BlockFilter filter;
	
	private CompositeGoal goal;
	
	public GetToBlockTask(BlockFilter filter) {
		this.filter = filter;
	}
	
	@Override
	public CompositeGoal onTick() {
		if(hasScanExpired() && !isScanning()) {
			
			rescan((positions) -> {
				
				goal = CompositeGoal.fromCollection(positions, pos -> new GetToBlockGoal(pos));
				
			}, filter);
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
	
	public BlockFilter getFilter() {
		return filter;
	}
	
}
