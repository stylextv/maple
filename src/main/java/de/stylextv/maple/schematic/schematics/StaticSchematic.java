package de.stylextv.maple.schematic.schematics;

import de.stylextv.maple.schematic.Schematic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class StaticSchematic extends Schematic {
	
	private Block[][][] blocks;
	
	public StaticSchematic(int width, int height, int length, Block[][][] blocks) {
		super(width, height, length);
		
		this.blocks = blocks;
	}
	
	@Override
	public Block getBlock(int x, int y, int z, BlockState state) {
		return blocks[x][y][z];
	}
	
	public Block[][][] getBlocks() {
		return blocks;
	}
	
}
