package de.stylextv.maple.world.scan.block.filters;

import de.stylextv.maple.world.scan.block.BlockFilter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BlockTypeFilter extends BlockFilter {
	
	private Block block;
	
	public BlockTypeFilter(Block block) {
		this.block = block;
	}
	
	@Override
	public boolean matches(BlockState state) {
		Block b = state.getBlock();
		
		return b.equals(block);
	}
	
	public Block getBlock() {
		return block;
	}
	
}
