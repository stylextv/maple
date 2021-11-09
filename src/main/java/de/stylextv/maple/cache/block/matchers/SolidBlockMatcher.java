package de.stylextv.maple.cache.block.matchers;

import de.stylextv.maple.cache.block.BlockMatcher;
import de.stylextv.maple.cache.block.BlockType;
import de.stylextv.maple.context.WorldContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

public class SolidBlockMatcher extends BlockMatcher {
	
	private static final Box UPPER_HALF = new Box(0, 0.5, 0, 1, 1, 1);
	
	@Override
	public BlockType match(BlockState state, BlockState above, BlockState below, BlockPos pos) {
		ClientWorld world = WorldContext.world();
		
		VoxelShape shape = state.getCollisionShape(world, pos);
		
		if(shape.isEmpty()) return null;
		
		Box box = shape.getBoundingBox();
		
		if(box.intersects(UPPER_HALF)) return BlockType.SOLID;
		
		return null;
	}
	
}
