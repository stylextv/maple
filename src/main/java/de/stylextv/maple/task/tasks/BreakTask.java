package de.stylextv.maple.task.tasks;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.stylextv.maple.input.target.BreakableTarget;
import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.pathing.calc.goal.TwoBlocksGoal;
import de.stylextv.maple.world.scan.block.BlockFilters;
import net.minecraft.util.math.BlockPos;

public abstract class BreakTask extends ScanTask {
	
	private Set<BreakableTarget> targets = ConcurrentHashMap.newKeySet();
	
	public BreakTask(BlockFilters filters) {
		super(filters);
	}
	
	@Override
	public CompositeGoal onTick() {
		if(targets.isEmpty()) {
			
			if(!isScanning()) {
				
				rescan((positions) -> {
					
					for(BlockPos pos : positions) {
						
						if(!hasTarget(pos)) addTarget(pos);
					}
					
				});
			}
			
			if(hasScanExpired()) return null;
		}
		
		for(BreakableTarget target : targets) {
			
			if(target.isBroken()) {
				
				removeTarget(target);
				
				continue;
			}
			
			if(target.isInReach()) {
				
				target.continueBreaking();
				
				return null;
			}
		}
		
		return CompositeGoal.fromCollection(targets, t -> new TwoBlocksGoal(t.getPos()));
	}
	
	public void addTarget(BlockPos pos) {
		BreakableTarget target = new BreakableTarget(pos);
		
		targets.add(target);
	}
	
	public void removeTarget(BreakableTarget target) {
		targets.remove(target);
	}
	
	public boolean hasTarget(BlockPos pos) {
		for(BreakableTarget target : targets) {
			
			BlockPos p = target.getPos();
			
			if(p.equals(pos)) return true;
		}
		
		return false;
	}
	
	public Set<BreakableTarget> getTargets() {
		return targets;
	}
	
}
