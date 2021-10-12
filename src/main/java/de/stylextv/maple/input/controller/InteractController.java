package de.stylextv.maple.input.controller;

import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.world.interact.Openable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

public class InteractController {
	
	public static void open(BlockPos pos, Openable o) {
		ViewController.lookAt(pos);
		
		BlockHitResult result = AwarenessController.getBlockTarget();
		
		if(result == null) return;
		
		BlockPos p = result.getBlockPos();
		
		if(p.equals(pos)) InputController.setPressed(InputAction.RIGHT_CLICK, true);
	}
	
}
