package de.stylextv.maple.world.interact.openables;

import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.interact.Openable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.util.math.Direction;

public class OpenableDoor extends Openable {
	
	@Override
	public boolean matchesBlock(Block block) {
		return block instanceof DoorBlock;
	}
	
	@Override
	public boolean isLocked(BlockState state) {
		Block block = state.getBlock();
		
		return block.equals(Blocks.IRON_DOOR);
	}
	
	@Override
	public boolean isOpen(BlockState state, Movement m) {
		if(m.isVerticalOnly() || m.isDiagonal()) return true;
		
		Direction dir = m.getDirection();
		
		Direction opposite = dir.getOpposite();
		
		Direction doorDir = state.get(DoorBlock.FACING);
		
		boolean open = state.get(DoorBlock.OPEN);
		
		boolean aligns = doorDir.equals(dir) || doorDir.equals(opposite);
		
		if(!aligns) open = !open;
		
		return open;
	}
	
}
