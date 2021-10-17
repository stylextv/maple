package de.stylextv.maple.task.tasks;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.stylextv.maple.input.target.BreakableTarget;
import de.stylextv.maple.pathing.calc.goal.BlockGoal;
import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.pathing.calc.goal.TwoBlocksGoal;
import de.stylextv.maple.world.scan.block.BlockFilters;
import de.stylextv.maple.world.scan.entity.EntityFilter;
import de.stylextv.maple.world.scan.entity.EntityFilters;
import de.stylextv.maple.world.scan.entity.EntityScanner;
import de.stylextv.maple.world.scan.entity.filters.EntityTypeFilter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;

public abstract class BreakTask extends ScanTask {
	
	private static final EntityFilter ITEM_FILTER = new EntityTypeFilter(EntityType.ITEM);
	
	private Set<BreakableTarget> targets = ConcurrentHashMap.newKeySet();
	
	public BreakTask(BlockFilters filters) {
		super(filters);
	}
	
	@Override
	public CompositeGoal onTick() {
		List<Entity> entities = EntityScanner.scanWorld(EntityFilters.fromFilter(ITEM_FILTER));
		
		CompositeGoal entityGoal = CompositeGoal.fromCollection(entities, e -> new BlockGoal(e.getBlockPos()));
		
		if(targets.isEmpty()) {
			
			if(!isScanning()) {
				
				rescan((pos) -> {
					
					if(!hasTarget(pos)) addTarget(pos);
					
				});
			}
			
			if(hasScanExpired()) {
				
				if(entityGoal.isEmpty()) return null;
				
				return entityGoal;
			}
		}
		
		for(BreakableTarget target : targets) {
			
			BlockState state = target.state();
			
			boolean broken = !getFilters().matches(state);
			
			if(broken) {
				
				removeTarget(target);
				
				continue;
			}
			
			if(target.isInReach()) {
				
				target.continueBreaking();
				
				return null;
			}
		}
		
		CompositeGoal blockGoal = CompositeGoal.fromCollection(targets, t -> new TwoBlocksGoal(t.getPos()));
		
		return CompositeGoal.combine(entityGoal, blockGoal);
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
