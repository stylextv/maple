package de.stylextv.maple.world.interact;

import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.interact.openables.OpenableDoor;
import de.stylextv.maple.world.interact.openables.OpenableFenceGate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public abstract class Openable {
	
	public static final Openable DOOR = new OpenableDoor();
	
	public static final Openable FENCE_GATE = new OpenableFenceGate();
	
	private static final Openable[] OPENABLES = new Openable[] {
			DOOR, FENCE_GATE
	};
	
	public abstract boolean matchesBlock(Block block);
	
	public abstract boolean isLocked(BlockState state);
	
	public abstract boolean isOpen(BlockState state, Movement m);
	
	public static Openable fromState(BlockState state) {
		Block block = state.getBlock();
		
		return fromBlock(block);
	}
	
	public static Openable fromBlock(Block block) {
		for(Openable o : OPENABLES) {
			
			if(o.matchesBlock(block)) return o;
		}
		
		return null;
	}
	
	public static Openable[] getOpenables() {
		return OPENABLES;
	}
	
}
