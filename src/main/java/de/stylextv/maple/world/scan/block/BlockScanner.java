package de.stylextv.maple.world.scan.block;

import java.util.function.Consumer;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.util.iterate.IntPair;
import de.stylextv.maple.util.iterate.iterators.SpiralIterator;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public class BlockScanner {
	
	private static final int SCAN_DISTANCE = 32;
	
	private static final int SCAN_LIMIT = 64;
	
	private static final SpiralIterator CHUNKS_ITERATOR = new SpiralIterator(SCAN_DISTANCE * 2);
	
	public static void scanWorld(BlockFilters filters, Consumer<BlockPos> consumer) {
		scanWorld(filters, SCAN_LIMIT, consumer);
	}
	
	public static void scanWorld(BlockFilters filters, int limit, Consumer<BlockPos> consumer) {
		ChunkPos pos = PlayerContext.chunkPosition();
		
		int centerX = pos.x;
		int centerZ = pos.z;
		
		int count = 0;
		
		for(IntPair pair : CHUNKS_ITERATOR) {
			
			int x = pair.getFirst();
			int z = pair.getSecond();
			
			int cx = centerX + x;
			int cz = centerZ + z;
			
			int n = scanChunk(cx, cz, filters, limit, count, consumer);
			
			count += n;
			
			if(count >= limit) return;
		}
	}
	
	// TODO start at player's y coordinate
	private static int scanChunk(int chunkX, int chunkZ, BlockFilters filters, int limit, int count, Consumer<BlockPos> consumer) {
		if(!WorldContext.isChunkLoaded(chunkX, chunkZ)) return 0;
		
		WorldChunk chunk = WorldContext.getChunk(chunkX, chunkZ);
		
		int height = chunk.getHeight();
		
		int bottomY = chunk.getBottomY();
		
		ChunkPos pos = chunk.getPos();
		
		int startX = pos.getStartX();
		int startZ = pos.getStartZ();
		
		int n = 0;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					
					int bx = startX + x;
					int by = bottomY + y;
					int bz = startZ + z;
					
					BlockPos p = new BlockPos(bx, by, bz);
					
					BlockState state = chunk.getBlockState(p);
					
					if(filters.matches(state)) {
						
						consumer.accept(p);
						
						n++;
						
						if(count + n >= limit) return n;
					}
				}
			}
		}
		
		return n;
	}
	
}
