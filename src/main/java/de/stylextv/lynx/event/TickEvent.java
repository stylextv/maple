package de.stylextv.lynx.event;

import de.stylextv.lynx.context.GameContext;
import de.stylextv.lynx.input.controller.BreakController;
import de.stylextv.lynx.input.controller.InputController;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.Type;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class TickEvent {
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if(event.type == Type.CLIENT && event.side == LogicalSide.CLIENT && event.phase == Phase.START) {
			
			if(!GameContext.isIngame()) return;
			
			BreakController.onTick();
			InputController.onTick();
		}
	}
	
}
