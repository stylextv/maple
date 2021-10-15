package de.stylextv.maple.world.scan.block;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.util.iterate.IntPair;
import de.stylextv.maple.util.iterate.iterators.GridIterator;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public class BlockScanner {
	
	private static final int SCAN_DISTANCE = 32;
	
	private static final int SCAN_LIMIT = 16;
	
	private static final GridIterator CHUNKS_ITERATOR = new GridIterator(SCAN_DISTANCE * 2, GridIterator.Type.SPIRAL);
	
	public static List<BlockPos> scanWorld(BlockFilters filters) {
		return scanWorld(filters, SCAN_LIMIT);
	}
	
	// TODO search in spiral pattern
	public static List<BlockPos> scanWorld(BlockFilters filters, int limit) {
		List<BlockPos> positions = new ArrayList<>();
		
		ChunkPos pos = PlayerContext.chunkPosition();
		
		int centerX = pos.x;
		int centerZ = pos.z;
		
		for(IntPair pair : CHUNKS_ITERATOR) {
			
			int x = pair.getFirst();
			int z = pair.getSecond();
			
			int cx = centerX + x;
			int cz = centerZ + z;
			
			boolean failed = scanChunk(cx, cz, filters, limit, positions);
			
			if(failed) return positions;
		}
		
		return positions;
	}
	
	// TODO start at player's y coordinate
	private static boolean scanChunk(int chunkX, int chunkZ, BlockFilters filters, int limit, List<BlockPos> positions) {
		if(!WorldContext.isChunkLoaded(chunkX, chunkZ)) return false;
		
		WorldChunk chunk = WorldContext.getChunk(chunkX, chunkZ);
		
		int height = chunk.getHeight();
		
		int bottomY = chunk.getBottomY();
		
		ChunkPos pos = chunk.getPos();
		
		int startX = pos.getStartX();
		int startZ = pos.getStartZ();
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					
					int bx = startX + x;
					int by = bottomY + y;
					int bz = startZ + z;
					
					BlockPos p = new BlockPos(bx, by, bz);
					
					BlockState state = chunk.getBlockState(p);
					
					if(filters.matches(state)) {
						
						positions.add(p);
						
						int l = positions.size();
						
						if(l == limit) return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
