package de.stylextv.maple.task.tasks;

import de.stylextv.maple.input.target.TargetList;
import de.stylextv.maple.input.target.targets.BreakableTarget;
import de.stylextv.maple.input.target.targets.PlaceableTarget;
import de.stylextv.maple.pathing.calc.goal.CompositeGoal;
import de.stylextv.maple.schematic.Schematic;
import net.minecraft.util.math.BlockPos;

public class BuildTask extends CompositeTask {
	
	private BlockPos pos;
	
	private Schematic schematic;
	
	private TargetList<BreakableTarget> breakTargets = new TargetList<>();
	private TargetList<PlaceableTarget> placeTargets = new TargetList<>();
	
	public BuildTask(BlockPos pos, Schematic s) {
		this.pos = pos;
		this.schematic = s;
	}
	
	@Override
	public CompositeGoal onRenderTick() {
		// TODO scan world using schematic and add targets
		
		
		for(BreakableTarget target : breakTargets) {
			
			if(target.isInReach() && target.continueBreaking()) return null;
		}
		
		for(PlaceableTarget target : placeTargets) {
			
			if(target.isInReach() && target.continuePlacing()) return null;
		}
		
		CompositeGoal breakGoal = breakTargets.toGoal();
		CompositeGoal placeGoal = placeTargets.toGoal();
		
		return CompositeGoal.combine(breakGoal, placeGoal);
	}
	
	@Override
	public void onFail() {
//		ChatUtil.send("Can't get any closer to block.");
	}
	
	@Override
	public void onComplete() {
//		ChatUtil.send("Can't find any matching blocks nearby.");
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
