package de.stylextv.maple.input.target;

import de.stylextv.maple.input.controller.InteractController;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.interact.Openable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class OpenableTarget extends BlockTarget {
	
	private Openable openable;
	
	public OpenableTarget(int x, int y, int z, Openable o) {
		super(x, y, z);
		
		updateOpenable(o);
	}
	
	public void updateOpenable(Openable o) {
		this.openable = o;
	}
	
	public void open(Movement m) {
		BlockPos pos = getPos();
		
		InteractController.open(pos, openable);
	}
	
	public boolean isLocked() {
		BlockState state = state();
		
		return openable.isLocked(state);
	}
	
	public boolean isOpen(Movement m) {
		BlockState state = state();
		
		return openable.isOpen(state, m);
	}
	
	public Openable getOpenable() {
		return openable;
	}
	
}
