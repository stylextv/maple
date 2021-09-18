package de.stylextv.lynx.pathing.calc;

import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.gui.ToolSet;
import de.stylextv.lynx.util.ItemUtil;
import de.stylextv.lynx.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

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
	
	public static final double BUMP_INTO_CORNER = 3.2;
	
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
		ClientWorld world = WorldContext.world();
		
		BlockState state = BlockInterface.getState(pos);
		
		float hardness = state.getHardness(world, pos);
		
		if(hardness < 0) return INFINITY;
		
		ToolSet tools = ToolSet.getTools();
		
		ItemStack stack = tools.getBestTool(state);
		
		float f = ItemUtil.getDigSpeed(stack, state);
		
		float damage = f / hardness;
		
		return 100 / damage;
	}
	
	public static double placeCost() {
		ToolSet tools = ToolSet.getTools();
		
		if(!tools.hasThrowawayBlocks()) return INFINITY;
		
		return PLACE_BLOCK;
	}
	
}
