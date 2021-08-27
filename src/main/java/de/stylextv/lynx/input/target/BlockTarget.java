package de.stylextv.lynx.input.target;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.AwarenessController;
import de.stylextv.lynx.input.controller.BreakController;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.PlaceController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.util.world.Offset;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;

public class BlockTarget {
	
	private BlockPos pos;
	
	public BlockTarget(int x, int y, int z) {
		this(new BlockPos(x, y, z));
	}
	
	public BlockTarget(BlockPos pos) {
		this.pos = pos;
	}
	
	public boolean continueTransforming() {
		if(isSelected()) {
			
			boolean b = isBreakable();
			
			InputAction i = b ? InputAction.LEFT_CLICK : InputAction.RIGHT_CLICK;
			
			InputController.setPressed(i, true);
			
			return true;
		}
		
		return lookAt();
	}
	
	public boolean lookAt() {
		Offset o;
		
		if(isPlaceable()) {
			
			o = visibleNeighbour();
			
		} else {
			
			o = visiblePoint();
		}
		
		if(o == null) return false;
		
		ViewController.lookAt(o);
		
		return true;
	}
	
	public Offset visibleNeighbour() {
		for(Offset o : Offset.BLOCK_NEIGHBOURS) {
			
			int x = (int) (pos.getX() + o.getX());
			int y = (int) (pos.getY() + o.getY());
			int z = (int) (pos.getZ() + o.getZ());
			
			if(PlaceController.canPlaceAgainst(x, y, z)) {
				
				double rx = pos.getX() + 0.5;
				double ry = pos.getY() + 0.5;
				double rz = pos.getZ() + 0.5;
				
				Offset o2 = o.divide(2);
				
				o2 = o2.add(rx, ry, rz);
				
				rx = o2.getX();
				ry = o2.getY();
				rz = o2.getZ();
				
				if(ViewController.canSee(o2)) return o2;
			}
		}
		
		return null;
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
		
		if(p.equals(pos)) return true;
		
		Direction dir = result.getDirection();
		
		return p.relative(dir).equals(pos);
	}
	
	public boolean isBreakable() {
		return BreakController.isBreakable(pos);
	}
	
	public boolean isPlaceable() {
		return !isBreakable();
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
}
