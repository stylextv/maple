package de.stylextv.maple.task.tasks;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.pathing.calc.goal.GetToBlockGoal;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.BlockInterface;
import de.stylextv.maple.world.scan.block.BlockFilter;
import de.stylextv.maple.world.scan.block.BlockFilters;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GetToBlockTask extends ScanTask {
	
	private Set<BlockPos> positions = ConcurrentHashMap.newKeySet();
	
	public GetToBlockTask(BlockFilter filter) {
		this(BlockFilters.fromFilter(filter));
	}
	
	public GetToBlockTask(BlockFilters filters) {
		super(filters);
	}
	
	@Override
	public CompositeGoal onRenderTick() {
		if(positions.isEmpty() && hasScanExpired()) {
			
			if(!isScanning()) {
				
				rescan((pos) -> {
					
					boolean contains = positions.contains(pos);
					
					if(!contains) positions.add(pos);
					
				});
			}
			
			return null;
		}
		
		for(BlockPos pos : positions) {
			
			BlockState state = BlockInterface.getState(pos);
			
			boolean matches = getFilters().matches(state);
			
			if(!matches) positions.remove(pos);
		}
		
		return CompositeGoal.fromCollection(positions, pos -> new GetToBlockGoal(pos));
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
