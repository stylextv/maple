package de.stylextv.maple.event.listeners;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.event.EventListener;
import de.stylextv.maple.event.events.TickEvent;
import de.stylextv.maple.input.controller.BreakController;
import de.stylextv.maple.input.controller.InputController;
import de.stylextv.maple.input.controller.PlaceController;
import de.stylextv.maple.input.controller.ViewController;
import de.stylextv.maple.pathing.movement.MovementExecutor;
import de.stylextv.maple.task.TaskManager;

public class TickListener implements EventListener {
	
	@Override
	public void onClientTick(TickEvent event) {
		if(!GameContext.isIngame()) return;
		
		TaskManager.onTick();
		
		BreakController.onTick();
		PlaceController.onTick();
		
		InputController.onTick();
	}
	
	@Override
	public void onRenderTick(TickEvent event) {
		if(!GameContext.isIngame()) return;
		
		MovementExecutor.onRenderTick();
		
		ViewController.onRenderTick();
	}
	
}
