package de.stylextv.maple.input.target;

import de.stylextv.maple.gui.ToolSet;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.input.controller.BreakController;
import de.stylextv.maple.input.controller.GuiController;
import de.stylextv.maple.input.controller.InputController;
import de.stylextv.maple.input.controller.PlaceController;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class TransformTarget extends BlockTarget {
	
	public TransformTarget(int x, int y, int z) {
		super(x, y, z);
	}
	
	public boolean continueTransforming() {
		if(isSelected(true)) {
			
			selectTool();
			
			boolean b = isBreakable();
			
			InputAction i = b ? InputAction.LEFT_CLICK : InputAction.RIGHT_CLICK;
			
			InputController.setPressed(i, true);
			
			return true;
		}
		
		boolean atNeighbour = isPlaceable();
		
		return lookAt(atNeighbour);
	}
	
	public void selectTool() {
		ToolSet tools = ToolSet.getTools();
		
		if(isBreakable()) {
			
			BlockState state = state();
			
			ItemStack stack = tools.getBestTool(state);
			
			GuiController.selectItem(stack);
			
			return;
		}
		
		if(tools.hasThrowawayBlocks()) {
			
			GuiController.selectItem(tools.getThrowawayBlocks());
		}
	}
	
	public boolean isBreakable() {
		return BreakController.isBreakable(getPos());
	}
	
	public boolean isPlaceable() {
		if(isBreakable()) return false;
		
		return PlaceController.canPlaceAt(getPos());
	}
	
}
