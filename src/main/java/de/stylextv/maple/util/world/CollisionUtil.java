package de.stylextv.maple.util.world;

import java.util.stream.Stream;

import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

public class CollisionUtil {
	
	public static boolean collidesWithBlocks(Box box) {
		Stream<BlockPos> stream = BlockPos.stream(box);
		
		boolean collides = stream.anyMatch(pos -> collidesWithBlock(box, pos));
		
		return collides;
	}
	
	private static boolean collidesWithBlock(Box box, BlockPos pos) {
		BlockState state = BlockInterface.getState(pos);
		
		return collidesWithBlock(box, pos, state);
	}
	
	public static boolean collidesWithBlock(Box box, BlockPos pos, BlockState state) {
		ClientWorld world = WorldContext.world();
		
		VoxelShape shape = state.getCollisionShape(world, pos);
		
		for(Box other : shape.getBoundingBoxes()) {
			
			other = other.offset(pos);
			
			if(other.intersects(box)) return true;
		}
		
		return false;
	}
	
}
