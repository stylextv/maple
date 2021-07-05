package de.stylextv.lynx.task;

import de.stylextv.lynx.pathing.goal.XZGoal;
import net.minecraft.block.Block;

public class MiningCycle extends Cycle {
	
	private Block[] blocks;
	
	public MiningCycle() {
		super("mine");
	}
	
	@Override
	public void start(String[] args) {
//		blocks = CommandManager.stringsToBlocks(args);
	}
	
	@Override
	public boolean run() {
		if(blocks.length == 0) {
			return true;
		}
		
		TaskManager.startTask(new MoveTask(new XZGoal(0, 0)));
		
//		BlockPos target = null;
//		
//		double targetViewDis = 0;
//		
//		for(BlockPos pos : InputManager.getBlocksInReach()) {
//			
//			boolean matches = false;
//			
//			Block block = InputManager.getBlock(pos);
//			
//			for(Block b : blocks) {
//				if(b.equals(block)) {
//					matches = true;
//					
//					break;
//				}
//			}
//			
//			if(matches && InputManager.canSee(pos)) {
//				
//				double viewDis = InputManager.getViewDistance(pos);
//				
//				if(target == null || viewDis < targetViewDis) {
//					target = pos;
//					targetViewDis = viewDis;
//				}
//			}
//		}
//		
//		if(target != null) {
//			TaskManager.startTask(new BreakBlockTask(target));
//		}
		
		return false;
	}
	
}
