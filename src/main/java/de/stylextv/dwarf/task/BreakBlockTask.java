package de.stylextv.dwarf.task;

import net.minecraft.util.math.BlockPos;

public class BreakBlockTask extends Task {
	
	private BlockPos pos;
	
	public BreakBlockTask(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public void start() {
//		InputManager.startMining(pos);
	}
	
	@Override
	public void stop() {
//		InputManager.stopMining();
	}
	
	@Override
	public boolean run() {
		return true;
//		return !InputManager.isMining();
	}
	
}
