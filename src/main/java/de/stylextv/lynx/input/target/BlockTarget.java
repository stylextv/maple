package de.stylextv.lynx.input.target;

import de.stylextv.lynx.context.LevelContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.AwarenessController;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.util.world.Offset;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockTarget {
	
	private BlockPos pos;
	
	public BlockTarget(int x, int y, int z) {
		this(new BlockPos(x, y, z));
	}
	
	public BlockTarget(BlockPos pos) {
		this.pos = pos;
	}
	
	public boolean continueBreaking() {
		if(isSelected()) {
			
			InputController.setPressed(InputAction.LEFT_CLICK, true);
			
			return true;
		}
		
		return lookAt();
	}
	
	public boolean isBreakable() {
		BlockState state = LevelContext.getBlockState(pos);
		
		return state.getMaterial().blocksMotion();
	}
	
	public boolean lookAt() {
		Offset o = visiblePoint();
		
		if(o == null) return false;
		
		ViewController.lookAt(o);
		
		return true;
	}
	
	public Offset visiblePoint() {
		Offset sum = new Offset();
		
		int n = 0;
		
		for(Offset o : Offset.BLOCK_CORNERS) {
			
			double x = pos.getX() + o.getX();
			double y = pos.getY() + o.getY();
			double z = pos.getZ() + o.getZ();
			
			if(ViewController.canSee(x, y, z)) {
				
				sum = sum.add(x, y, z);
				
				n++;
			}
		}
		
		if(n == 0) return null;
		
		return sum.divide(n);
	}
	
	public boolean isSelected() {
		BlockHitResult result = AwarenessController.getBlockUnderCrosshair();
		
		if(result == null) return false;
		
		BlockPos p = result.getBlockPos();
		
		return p.equals(pos);
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
}
