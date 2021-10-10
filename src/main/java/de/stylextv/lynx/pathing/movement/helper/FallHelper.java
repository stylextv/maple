package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.context.WorldContext;
import de.stylextv.lynx.gui.ToolSet;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.GuiController;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.movements.FallMovement;
import net.minecraft.item.ItemStack;
import net.minecraft.world.dimension.DimensionType;

public class FallHelper extends MovementHelper<FallMovement> {
	
	private boolean placedWaterBucket;
	
	public FallHelper(FallMovement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		if(placedWaterBucket) return 0;
		
		if(hasToMlg()) {
			
			DimensionType dimension = WorldContext.dimension();
			
			if(dimension.isUltrawarm()) return Cost.INFINITY;
			
			ToolSet tools = ToolSet.getTools();
			
			if(!tools.hasWaterBucket()) return Cost.INFINITY;
		}
		
		return 0;
	}
	
	public boolean hasToMlg() {
		FallMovement m = getMovement();
		
		int fallDistance = m.getFallDistance();
		
		if(fallDistance < 4) return false;
		
		Node destination = m.getDestination();
		
		BlockType type = destination.getType();
		
		return type != BlockType.WATER;
	}
	
	public void onRenderTick() {
		boolean onGround = PlayerContext.isOnGround();
		
		if(onGround || !hasToMlg() || !selectWaterBucket()) return;
		
		InputController.setPressed(InputAction.RIGHT_CLICK, true);
		
		placedWaterBucket = true;
	}
	
	private boolean selectWaterBucket() {
		ToolSet tools = ToolSet.getTools();
		
		if(!tools.hasWaterBucket()) return false;
		
		ItemStack stack = tools.getWaterBucket();
		
		GuiController.selectItem(stack);
		
		return true;
	}
	
	public boolean isFinished() {
		boolean onGround = PlayerContext.isOnGround();
		
		boolean inWater = PlayerContext.isInWater();
		
		if(!inWater) return onGround;
		
		if(placedWaterBucket) InputController.setPressed(InputAction.RIGHT_CLICK, true);
		
		return false;
	}
	
}
