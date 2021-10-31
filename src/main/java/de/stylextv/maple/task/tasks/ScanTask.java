package de.stylextv.maple.task.tasks;

import java.util.function.Consumer;

import de.stylextv.maple.util.async.AsyncUtil;
import de.stylextv.maple.world.scan.block.BlockFilters;
import de.stylextv.maple.world.scan.block.BlockScanner;
import net.minecraft.util.math.BlockPos;

public abstract class ScanTask extends CompositeTask {
	
	private static final long SCAN_EXPIRATION_TIME = 5000;
	
	private BlockFilters filters;
	
	private boolean scanning;
	
	private long scanEndTime;
	
	public ScanTask(BlockFilters filters, boolean completeAtGoal) {
		super(completeAtGoal);
		
		this.filters = filters;
	}
	
	public void rescan(Consumer<BlockPos> consumer) {
		if(scanning) return;
		
		scanning = true;
		
		AsyncUtil.runAsync(() -> {
			
			BlockScanner.scanWorld(filters, consumer);
			
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
