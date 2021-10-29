package de.stylextv.maple.task.tasks;

import java.util.List;

import de.stylextv.maple.input.target.TargetList;
import de.stylextv.maple.input.target.targets.BreakableTarget;
import de.stylextv.maple.pathing.calc.goal.BlockGoal;
import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.world.scan.block.BlockFilters;
import de.stylextv.maple.world.scan.entity.EntityFilter;
import de.stylextv.maple.world.scan.entity.EntityFilters;
import de.stylextv.maple.world.scan.entity.EntityScanner;
import de.stylextv.maple.world.scan.entity.filters.EntityTypeFilter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class BreakTask extends ScanTask {
	
	private static final EntityFilter ITEM_FILTER = new EntityTypeFilter(EntityType.ITEM);
	
	private EntityFilters entityFilters;
	
	private TargetList<BreakableTarget> targets = new TargetList<>();
	
	public BreakTask(BlockFilters filters) {
		super(filters);
		
		EntityFilter filter = new EntityFilter(e -> {
			
			ItemEntity entity = (ItemEntity) e;
			
			ItemStack stack = entity.getStack();
			
			Item item = stack.getItem();
			
			if(!(item instanceof BlockItem)) return false;
			
			Block block = ((BlockItem) item).getBlock();
			
			return getFilters().matches(block.getDefaultState());
		});
		
		this.entityFilters = EntityFilters.fromFilters(ITEM_FILTER, filter);
	}
	
	@Override
	public CompositeGoal onRenderTick() {
		List<Entity> entities = EntityScanner.scanWorld(entityFilters);
		
		CompositeGoal entityGoal = CompositeGoal.fromCollection(entities, e -> new BlockGoal(e.getBlockPos()));
		
		if(targets.isEmpty()) {
			
			if(!isScanning()) {
				
				rescan((pos) -> {
					
					boolean contains = targets.contains(pos);
					
					if(!contains) {
						
						BreakableTarget target = new BreakableTarget(pos);
						
						targets.add(target);
					}
					
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
				
				targets.remove(target);
				
				continue;
			}
			
			if(target.isInReach() && target.continueBreaking()) return null;
		}
		
		CompositeGoal blockGoal = targets.toGoal();
		
		return CompositeGoal.combine(entityGoal, blockGoal);
	}
	
	public TargetList<BreakableTarget> getTargets() {
		return targets;
	}
	
}
