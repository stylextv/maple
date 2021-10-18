package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.input.controller.AwarenessController;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class JumpHelper extends MovementHelper<Movement> {
	
	public JumpHelper(Movement m) {
		super(m);
	}
	
	public boolean shouldJump() {
		if(!canJump()) return false;
		
		Movement m = getMovement();
		
		Node destination = m.getDestination();
		
		int destinationY = destination.getY();
		
		BlockPos pos = PlayerContext.feetPosition();
		
		int y = pos.getY();
		
		if(y >= destinationY) return false;
		
		BlockPos below = destination.blockPos().down();
		
		BlockState state = BlockInterface.getState(below);
		
		Direction dir = m.getDirection();
		
		return !canWalkUp(state, dir);
	}
	
	private boolean canWalkUp(BlockState state, Direction dir) {
		Block block = state.getBlock();
		
		if(block instanceof SlabBlock) {
			
			SlabType type = state.get(SlabBlock.TYPE);
			
			return type == SlabType.BOTTOM;
		}
		
		if(block instanceof StairsBlock) {
			
			BlockHalf half = state.get(StairsBlock.HALF);
			
			if(half == BlockHalf.TOP) return false;
			
			Direction stairsDir = state.get(StairsBlock.FACING);
			
			return stairsDir.equals(dir);
		}
		
		return false;
	}
	
	public boolean canJump() {
		return AwarenessController.canJump();
	}
	
}
