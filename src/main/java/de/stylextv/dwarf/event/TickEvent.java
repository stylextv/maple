package de.stylextv.dwarf.event;

import de.stylextv.dwarf.input.PlayerContext;
import de.stylextv.dwarf.input.controller.InputController;
import de.stylextv.dwarf.input.controller.MovementController;
import de.stylextv.dwarf.input.controller.ViewController;
import de.stylextv.dwarf.task.TaskManager;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.Type;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class TickEvent {
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if(event.type == Type.CLIENT && event.side == LogicalSide.CLIENT && event.phase == Phase.START) {
			
			if(!PlayerContext.isIngame()) return;
			
			TaskManager.onTick();
			InputController.onTick();
			MovementController.onTick();
			ViewController.onTick();
		}
	}
	
}
