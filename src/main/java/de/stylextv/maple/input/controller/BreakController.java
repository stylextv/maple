package de.stylextv.maple.input.controller;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.mixin.MinecraftClientInvoker;
import de.stylextv.maple.util.world.Offset;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

public class BreakController {
	
	public static boolean isSafeToBreak(BlockPos pos) {
		BlockState state = BlockInterface.getState(pos);
		
		boolean inFluid = !state.getFluidState().isEmpty();
		
		if(inFluid) return true;
		
		for(Offset o : Offset.UPPER_DIRECT_BLOCK_NEIGHBOURS) {
			
			int dx = o.getBlockX();
			int dy = o.getBlockY();
			int dz = o.getBlockZ();
			
			int x = pos.getX() + dx;
			int y = pos.getY() + dy;
			int z = pos.getZ() + dz;
			
			state = BlockInterface.getState(x, y, z);
			
			int level = state.getFluidState().getLevel();
			
			if(level == 0) continue;
			
			if(dy == 1 || level > 1) return false;
		}
		
		return true;
	}
	
	public static boolean isBreakable(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		
		return isBreakable(pos);
	}
	
	public static boolean isBreakable(BlockPos pos) {
		BlockState state = BlockInterface.getState(pos);
		
		ClientWorld world = WorldContext.world();
		
		VoxelShape shape = state.getOutlineShape(world, pos);
		
		return !shape.isEmpty();
	}
	
	public static void onTick() {
		boolean b = InputController.isPressed(InputAction.LEFT_CLICK);
		
		if(!b) return;
		
		InputController.setPressed(InputAction.LEFT_CLICK, false);
		
		MinecraftClientInvoker invoker = GameContext.clientInvoker();
		
		invoker.invokeHandleBlockBreaking(true);
	}
	
}
