package de.stylextv.maple.cache.block.matchers;

import de.stylextv.maple.cache.block.BlockMatcher;
import de.stylextv.maple.cache.block.BlockType;
import de.stylextv.maple.util.world.CollisionUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class SolidBlockMatcher extends BlockMatcher {
	
	private static final Box COLLISION_BOX = new Box(0, 0.5, 0, 1, 1.5, 1);
	
	@Override
	public BlockType match(BlockState state, BlockState above, BlockState below, BlockPos pos) {
		if(!CollisionUtil.hasCollision(pos, state)) return null;
		
		Box box = COLLISION_BOX.offset(pos);
		
		boolean collides = CollisionUtil.collidesWithBlock(box, pos, state) || CollisionUtil.collidesWithBlock(box, pos.up(), above);
		
		if(collides) return BlockType.SOLID;
		
		return null;
	}
	
}
