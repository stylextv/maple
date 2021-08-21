package de.stylextv.lynx.event;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.input.controller.BreakController;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.movement.MovementExecutor;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TickEvent {
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if(event.phase != Phase.START || !GameContext.isIngame()) return;
		
		BreakController.onTick();
		InputController.onTick();
	}
	
	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event) {
		if(event.phase != Phase.START || !GameContext.isIngame()) return;
		
		MovementExecutor.onRenderTick();
		
		BreakController.onRenderTick();
		ViewController.onRenderTick();
	}
	
}
