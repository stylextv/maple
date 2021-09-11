package de.stylextv.lynx.event.events;

import de.stylextv.lynx.context.GameContext;
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
		
		InputController.onTick();
	}
	
	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event) {
		if(event.phase != Phase.START || !GameContext.isIngame()) return;
		
		MovementExecutor.onRenderTick();
		
		ViewController.onRenderTick();
	}
	
}
