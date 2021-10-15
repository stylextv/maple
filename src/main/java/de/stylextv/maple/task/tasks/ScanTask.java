package de.stylextv.maple.task.tasks;

import java.util.List;
import java.util.function.Consumer;

import de.stylextv.maple.util.async.AsyncUtil;
import de.stylextv.maple.world.scan.block.BlockFilters;
import de.stylextv.maple.world.scan.block.BlockScanner;
import net.minecraft.util.math.BlockPos;

public abstract class ScanTask extends CompositeTask {
	
	private static final long SCAN_EXPIRATION_TIME = 15000;
	
	private BlockFilters filters;
	
	private boolean scanning;
	
	private long scanEndTime;
	
	public ScanTask(BlockFilters filters) {
		this.filters = filters;
	}
	
	public void rescan(Consumer<List<BlockPos>> consumer) {
		if(scanning) return;
		
		scanning = true;
		
		AsyncUtil.runAsync(() -> {
			
			List<BlockPos> positions = BlockScanner.scanWorld(filters);
			
			consumer.accept(positions);
			
			scanEndTime = System.currentTimeMillis();
			
			scanning = false;
		});
	}
	
	public boolean hasScanExpired() {
		long now = System.currentTimeMillis();
		
		long elapsedTime = now - scanEndTime;
		
		return elapsedTime > SCAN_EXPIRATION_TIME;
	}
	
	public boolean isScanning() {
		return scanning;
	}
	
	public BlockFilters getFilters() {
		return filters;
	}
	
}
