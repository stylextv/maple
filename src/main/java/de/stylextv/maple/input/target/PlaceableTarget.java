package de.stylextv.maple.input.target;

import de.stylextv.maple.gui.ToolSet;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.input.controller.BreakController;
import de.stylextv.maple.input.controller.GuiController;
import de.stylextv.maple.input.controller.InputController;
import net.minecraft.util.math.BlockPos;

public class PlaceableTarget extends BlockTarget {
	
	public PlaceableTarget(int x, int y, int z) {
		super(x, y, z);
	}
	
	public PlaceableTarget(BlockPos pos) {
		super(pos);
	}
	
	public boolean continuePlacing() {
		if(isSelected(false)) {
			
			ToolSet tools = ToolSet.getTools();
			
			if(tools.hasThrowawayBlocks()) {
				
				GuiController.selectItem(tools.getThrowawayBlocks());
			}
			
			InputController.setPressed(InputAction.RIGHT_CLICK, true);
			
			return true;
		}
		
		return lookAt(true);
	}
	
	public boolean isPlaced() {
		BlockPos pos = getPos();
		
		return BreakController.isBreakable(pos);
	}
	
}
