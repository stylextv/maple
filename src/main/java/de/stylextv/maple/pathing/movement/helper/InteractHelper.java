package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.input.target.OpenableTarget;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.BlockInterface;
import de.stylextv.maple.world.interact.Openable;
import net.minecraft.block.BlockState;

public class InteractHelper extends TargetHelper<OpenableTarget> {
	
	public InteractHelper(Movement m) {
		super(m);
	}
	
	public void collectDefaultBlocks() {
		System.out.println("collecting defaults");
		
		Movement m = getMovement();
		
		Node source = m.getSource();
		Node destination = m.getDestination();
		
		collectBlocks(source, 2);
		collectBlocks(destination, 2);
	}
	
	@Override
	public void collectBlock(int x, int y, int z) {
		BlockState state = BlockInterface.getState(x, y, z);
		
		Openable o = Openable.fromState(state);
		
		if(o == null) return;
		
		OpenableTarget target = getTarget(x, y, z);
		
		if(target == null) {
			
			target = new OpenableTarget(x, y, z, o);
			
			addTarget(target);
			
			return;
		}
		
		target.updateOpenable(o);
	}
	
	@Override
	public double cost() {
		for(OpenableTarget target : getTargets()) {
			
			if(target.isLocked()) return Cost.INFINITY;
		}
		
		return 0;
	}
	
	public boolean onRenderTick() {
		if(!hasTargets()) return false;
		
		Movement m = getMovement();
		
		for(OpenableTarget target : getTargets()) {
			
			if(target.isOpen(m)) {
				
				removeTarget(target);
				
				continue;
			}
			
			if(target.open()) return true;
		}
		
		return false;
	}
	
}
