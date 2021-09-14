package de.stylextv.lynx.event.events;

import de.stylextv.lynx.event.Event;
import de.stylextv.lynx.event.EventListener;
import net.minecraft.util.math.BlockPos;

public class BlockUpdateEvent extends Event {
	
	private BlockPos pos;
	
	public BlockUpdateEvent(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public void callListener(EventListener l) {
		l.onBlockUpdate(this);
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
}
