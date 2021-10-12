package de.stylextv.maple.world.interact.openables;

import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.interact.Openable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrapdoorBlock;

public class OpenableTrapdoor extends Openable {
	
	@Override
	public boolean matchesBlock(Block block) {
		return block instanceof TrapdoorBlock;
	}
	
	@Override
	public boolean isLocked(BlockState state) {
		Block block = state.getBlock();
		
		return block.equals(Blocks.IRON_TRAPDOOR);
	}
	
	@Override
	public boolean isOpen(BlockState state, Movement m) {
		boolean open = state.get(TrapdoorBlock.OPEN);
		
		boolean b = m.isVerticalOnly() || m.isDownwards();
		
		if(!b) open = !open;
		
		return open;
	}
	
}
