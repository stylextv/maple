package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.input.controller.GuiController;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class Cost {
	
	public static final double INFINITY = 100000000;
	
	public static final double WALK_STRAIGHT = 4.6328;
	public static final double WALK_DIAGONALLY = 6.5518;
	
	public static final double SPRINT_STRAIGHT = 3.5638;
	public static final double SPRINT_DIAGONALLY = 5.04;
	
	public static final double SWIM_STRAIGHT = 9.0909;
	public static final double SWIM_DIAGONALLY = 12.8565;
	
	public static final double JUMP = fallCost(1.25) - fallCost(0.25);
	
	public static final double[] FALL_N_BLOCKS = new double[255];
	
	private static final int PLACE_BLOCK = 20;
	
	static {
		for(int i = 0; i < FALL_N_BLOCKS.length; i++) {
			FALL_N_BLOCKS[i] = fallCost(i);
		}
	}
	
	public static double fallCost(double dis) {
		if(dis == 0) return 0;
		
		return Math.sqrt(dis * 25.5102);
	}
	
	public static double breakCost(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		return breakCost(x, y, z);
	}
	
	// TODO factor in potion effects, enchantments, ...
	public static double breakCost(int x, int y, int z) {
		ClientLevel level = LevelContext.level();
		
		BlockPos pos = new BlockPos(x, y, z);
		
		BlockState state = level.getBlockState(pos);
		
		float hardness = state.getDestroySpeed(level, pos);
		
		if(hardness < 0) return INFINITY;
		
		ItemStack stack = GuiController.bestTool(state);
		
		float f = 1;
		
		if(stack != null) f = stack.getDestroySpeed(state);
		
		float damage = f / hardness;
		
		return 100 / damage;
	}
	
	public static double placeCost() {
		if(!GuiController.hasBuildingMaterial()) return INFINITY;
		
		return PLACE_BLOCK;
	}
	
}
