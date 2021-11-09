package de.stylextv.maple.cache.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class BlockType {
	
	public static final int BIT_AMOUNT = 3;
	
	public static final BlockType AIR = new BlockType("air", 0, Blocks.AIR).passable(true).air(true);
	public static final BlockType SOLID = new BlockType("solid", 1, Blocks.STONE).solid(true).breakable(true);
	public static final BlockType UNBREAKABLE = new BlockType("unbreakable", 2, Blocks.BEDROCK).solid(true);
	public static final BlockType WATER = new BlockType("water", 3, Blocks.WATER).passable(true);
	public static final BlockType DANGER = new BlockType("danger", 4, Blocks.LAVA);
	
	public static final BlockType UNLOADED = new BlockType("unloaded", Blocks.AIR).air(true);
	public static final BlockType VOID = new BlockType("void", Blocks.AIR).passable(true).air(true);
	
	private String name;
	
	private boolean[] bits;
	
	private BlockState state;
	
	private boolean passable;
	
	private boolean solid;
	
	private boolean breakable;
	
	private boolean air;
	
	private BlockType(String name, Block block) {
		this(name, -1, block);
	}
	
	private BlockType(String name, int bits, Block block) {
		this.name = name;
		this.bits = new boolean[] {
				(bits & 4) != 0,
				(bits & 2) != 0,
				(bits & 1) != 0
		};
		
		this.state = block.getDefaultState();
	}
	
	public BlockType passable(boolean b) {
		this.passable = b;
		
		return this;
	}
	
	public BlockType solid(boolean b) {
		this.solid = b;
		
		return this;
	}
	
	public BlockType breakable(boolean b) {
		this.breakable = b;
		
		return this;
	}
	
	public BlockType air(boolean b) {
		this.air = b;
		
		return this;
	}
	
	public boolean isUnloaded() {
		return this == UNLOADED;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean[] getBits() {
		return bits;
	}
	
	public BlockState getState() {
		return state;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isBreakable() {
		return breakable;
	}
	
	public boolean isAir() {
		return air;
	}
	
	public static BlockType fromBits(boolean b1, boolean b2, boolean b3) {
		return b1 ? DANGER : b2 ? b3 ? WATER : UNBREAKABLE : b3 ? SOLID : AIR;
	}
	
}
