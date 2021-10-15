package de.stylextv.maple.task.tasks;

import java.util.List;
import java.util.function.Consumer;

import de.stylextv.maple.util.async.AsyncUtil;
import de.stylextv.maple.world.scan.block.BlockFilter;
import de.stylextv.maple.world.scan.block.BlockScanner;
import net.minecraft.util.math.BlockPos;

public abstract class ScanTask extends CompositeTask {
	
	private boolean scanned;
	
	private long scanEndTime;
	
	private boolean scanning;
	
	public void rescan(Consumer<List<BlockPos>> consumer, BlockFilter... filters) {
		if(scanning) return;
		
		scanning = true;
		
		AsyncUtil.runAsync(() -> {
			
			List<BlockPos> positions = BlockScanner.scanWorld(filters);
			
			consumer.accept(positions);
			
			scanned = true;
			
			scanEndTime = System.currentTimeMillis();
			
			scanning = false;
		});
	}
	
	public boolean hasScanned() {
		return scanned;
	}
	
	public long getScanEndTime() {
		return scanEndTime;
	}
	
	public boolean isScanning() {
		return scanning;
	}
	
}
