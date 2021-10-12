package de.stylextv.maple.world.interact.openables;

import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.interact.Openable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;

public class OpenableFenceGate extends Openable {
	
	@Override
	public boolean matchesBlock(Block block) {
		return block instanceof FenceGateBlock;
	}
	
	@Override
	public boolean isLocked(BlockState block) {
		return false;
	}
	
	@Override
	public boolean isOpen(BlockState state, Movement m) {
		boolean open = state.get(FenceGateBlock.OPEN);
		
		return open;
	}
	
}
