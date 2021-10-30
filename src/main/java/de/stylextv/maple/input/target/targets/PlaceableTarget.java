package de.stylextv.maple.input.target.targets;

import de.stylextv.maple.gui.ToolSet;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.input.controller.BreakController;
import de.stylextv.maple.input.controller.GuiController;
import de.stylextv.maple.input.controller.InputController;
import de.stylextv.maple.input.target.BlockTarget;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class PlaceableTarget extends BlockTarget {
	
	private Block block;
	
	public PlaceableTarget(int x, int y, int z, Block block) {
		this(x, y, z);
		
		this.block = block;
	}
	
	public PlaceableTarget(int x, int y, int z) {
		super(x, y, z);
	}
	
	public PlaceableTarget(BlockPos pos) {
		super(pos);
	}
	
	public boolean continuePlacing() {
		if(isSelected(false)) {
			
			if(hasMaterial()) {
				
				GuiController.selectItem(getMaterial());
				
				InputController.setPressed(InputAction.RIGHT_CLICK, true);
			}
			
			return true;
		}
		
		return lookAt(true);
	}
	
	public boolean hasMaterial() {
		return !getMaterial().equals(ItemStack.EMPTY);
	}
	
	public ItemStack getMaterial() {
		ToolSet tools = ToolSet.getTools();
		
		if(block == null) return tools.getThrowawayBlocks();
		
		return tools.getStackOf(block);
	}
	
	public boolean isPlaced() {
		BlockPos pos = getPos();
		
		return BreakController.isBreakable(pos);
	}
	
	public Block getBlock() {
		return block;
	}
	
}
