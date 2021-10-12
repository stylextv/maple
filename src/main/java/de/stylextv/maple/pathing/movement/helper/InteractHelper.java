package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.input.controller.InteractController;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.BlockInterface;
import de.stylextv.maple.world.interact.Openable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class InteractHelper extends MovementHelper<Movement> {
	
	public InteractHelper(Movement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		Movement m = getMovement();
		
		Node source = m.getSource();
		Node destination = m.getDestination();
		
		if(isLocked(source) || isLocked(destination)) return Cost.INFINITY;
		
		return 0;
	}
	
	private boolean isLocked(Node n) {
		BlockPos pos = n.blockPos();
		
		BlockState state = BlockInterface.getState(pos);
		
		Openable o = Openable.fromState(state);
		
		if(o == null) return false;
		
		return o.isLocked(state);
	}
	
	public boolean onRenderTick() {
		Movement m = getMovement();
		
		if(m.isDiagonal()) return false;
		
		Node source = m.getSource();
		Node destination = m.getDestination();
		
		return tryOpening(source) || tryOpening(destination);
	}
	
	private boolean tryOpening(Node n) {
		BlockPos pos = n.blockPos();
		
		BlockState state = BlockInterface.getState(pos);
		
		Openable o = Openable.fromState(state);
		
		return tryOpening(pos, state, o);
	}
	
	private boolean tryOpening(BlockPos pos, BlockState state, Openable o) {
		if(o == null) return false;
		
		boolean open = o.isOpen(state, getMovement());
		
		if(open) return false;
		
		InteractController.open(pos, o);
		
		return true;
	}
	
}
