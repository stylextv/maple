package de.stylextv.maple.util.world;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

public class CollisionUtil {
	
	public static boolean collidesWithEntities(Box box) {
		Predicate<Entity> predicate = e -> {
			return true;
		};
		
		return collidesWithEntities(box, predicate);
	}
	
	public static boolean collidesWithEntities(Box box, EntityType<?> type) {
		Predicate<Entity> predicate = e -> e.getType().equals(type);
		
		return collidesWithEntities(box, predicate);
	}
	
	public static boolean collidesWithEntities(Box box, Predicate<Entity> predicate) {
		ClientWorld world = WorldContext.world();
		
		ClientPlayerEntity p = PlayerContext.player();
		
		List<Entity> list = world.getOtherEntities(p, box, predicate);
		
		return !list.isEmpty();
	}
	
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
	
	public static boolean hasCollision(BlockPos pos) {
		BlockState state = BlockInterface.getState(pos);
		
		return hasCollision(pos, state);
	}
	
	public static boolean hasCollision(BlockPos pos, BlockState state) {
		ClientWorld world = WorldContext.world();
		
		VoxelShape shape = state.getCollisionShape(world, pos);
		
		return !shape.isEmpty();
	}
	
}
