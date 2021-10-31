package de.stylextv.maple.task.tasks;

import de.stylextv.maple.input.controller.BreakController;
import de.stylextv.maple.input.target.TargetList;
import de.stylextv.maple.input.target.targets.BreakableTarget;
import de.stylextv.maple.input.target.targets.PlaceableTarget;
import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.pathing.movement.MovementExecutor;
import de.stylextv.maple.schematic.Schematic;
import de.stylextv.maple.util.chat.ChatUtil;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BuildTask extends CompositeTask {
	
	private BlockPos pos;
	
	private Schematic schematic;
	
	private TargetList<BreakableTarget> breakTargets = new TargetList<>();
	private TargetList<PlaceableTarget> placeTargets = new TargetList<>();
	
	public BuildTask(BlockPos pos, Schematic s) {
		super(false);
		
		this.pos = pos;
		this.schematic = s;
	}
	
	@Override
	public CompositeGoal onRenderTick() {
		int originX = pos.getX();
		int originY = pos.getY();
		int originZ = pos.getZ();
		
		for(int x = 0; x < schematic.getWidth(); x++) {
			for(int y = 0; y < schematic.getHeight(); y++) {
				for(int z = 0; z < schematic.getLength(); z++) {
					
					int bx = originX + x;
					int by = originY + y;
					int bz = originZ + z;
					
					BlockState state = BlockInterface.getState(bx, by, bz);
					
					Block block = schematic.getBlock(x, y, z, state);
					
					boolean matches = state.getBlock().equals(block);
					
					if(matches) {
						
						breakTargets.remove(pos);
						placeTargets.remove(pos);
						
						continue;
					}
					
					boolean b = BreakController.isBreakable(bx, by, bz);
					
					if(b) {
						
						if(breakTargets.contains(bx, by, bz)) continue;
						
						breakTargets.add(new BreakableTarget(bx, by, bz));
						
					} else {
						
						if(placeTargets.contains(bx, by, bz)) continue;
						
						placeTargets.add(new PlaceableTarget(bx, by, bz, block));
					}
				}
			}
		}
		
		if(MovementExecutor.isSafeToPause()) {
			
			for(BreakableTarget target : breakTargets) {
				
				if(target.isBroken()) {
					
					breakTargets.remove(target);
					
					continue;
				}
				
				if(target.isInReach() && target.continueBreaking()) return null;
			}
			
			for(PlaceableTarget target : placeTargets) {
				
				if(target.isPlaced()) {
					
					placeTargets.remove(target);
					
					continue;
				}
				
				if(target.isInReach() && target.continuePlacing()) return null;
			}
		}
		
		CompositeGoal breakGoal = breakTargets.toGoal();
		CompositeGoal placeGoal = placeTargets.toGoal();
		
		return CompositeGoal.combine(breakGoal, placeGoal);
	}
	
	@Override
	public void onFail() {
		ChatUtil.send("Can't continue building structure.");
	}
	
	@Override
	public void onEmptyGoal() {
		ChatUtil.send("Finished building.");
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public Schematic getSchematic() {
		return schematic;
	}
	
	public TargetList<BreakableTarget> getBreakTargets() {
		return breakTargets;
	}
	
	public TargetList<PlaceableTarget> getPlaceTargets() {
		return placeTargets;
	}
	
}
