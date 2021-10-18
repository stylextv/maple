package de.stylextv.maple.input.controller;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.mixin.MinecraftClientInvoker;
import de.stylextv.maple.util.world.Offset;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class PlaceController {
	
	public static boolean canPlaceAt(BlockPos pos) {
		for(Offset o : Offset.DIRECT_BLOCK_NEIGHBOURS) {
			
			int x = pos.getX() + o.getBlockX();
			int y = pos.getY() + o.getBlockY();
			int z = pos.getZ() + o.getBlockZ();
			
			Direction dir = o.getDirection().getOpposite();
			
			if(canPlaceAgainst(x, y, z, dir)) return true;
		}
		
		return false;
	}
	
	public static boolean canPlaceAgainst(int x, int y, int z, Direction dir) {
		BlockPos pos = new BlockPos(x, y, z);
		
		BlockState state = BlockInterface.getState(pos);
		
		ClientWorld world = WorldContext.world();
		
		VoxelShape shape = state.getOutlineShape(world, pos);
		
		return Block.isFaceFullSquare(shape, dir);
	}
	
	public static void onTick() {
		boolean b = InputController.isPressed(InputAction.RIGHT_CLICK);
		
		if(!b) return;
		
		InputController.setPressed(InputAction.RIGHT_CLICK, false);
		
		MinecraftClientInvoker invoker = GameContext.clientInvoker();
		
		invoker.invokeDoItemUse();
	}
	
}
