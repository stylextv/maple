package de.stylextv.maple.world.scan.block.filters;

import de.stylextv.maple.world.scan.block.BlockFilter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BlockTypeFilter extends BlockFilter {
	
	private Block[] blocks;
	
	public BlockTypeFilter(Block... blocks) {
		this.blocks = blocks;
	}
	
	@Override
	public boolean matches(BlockState state) {
		Block block = state.getBlock();
		
		for(Block b : blocks) {
			
			if(block.equals(b)) return true;
		}
		
		return false;
	}
	
	public Block[] getBlocks() {
		return blocks;
	}
	
}
