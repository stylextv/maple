package de.stylextv.lynx.event.listeners;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.event.EventListener;
import de.stylextv.lynx.event.events.ClientTickEvent;
import de.stylextv.lynx.event.events.RenderTickEvent;
import de.stylextv.lynx.input.controller.BreakController;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.PlaceController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.movement.MovementExecutor;

public class TickListener implements EventListener {
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(!GameContext.isIngame()) return;
		
		BreakController.onTick();
		PlaceController.onTick();
		
		InputController.onTick();
	}
	
	@Override
	public void onRenderTick(RenderTickEvent event) {
		if(!GameContext.isIngame()) return;
		
		MovementExecutor.onRenderTick();
		
		ViewController.onRenderTick();
	}
	
}
