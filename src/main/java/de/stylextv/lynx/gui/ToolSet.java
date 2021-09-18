package de.stylextv.lynx.gui;

import java.util.HashMap;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.util.ItemUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;

public class ToolSet {
	
	private static ToolSet tools;
	
	private DefaultedList<ItemStack> stacks;
	
	private int size;
	
	private HashMap<BlockState, ItemStack> bestTools = new HashMap<>();
	
	private ItemStack throwawayBlocks;
	
	private ItemStack waterBucket;
	
	public ToolSet(DefaultedList<ItemStack> stacks, int size) {
		this.stacks = stacks;
		this.size = size;
	}
	
	public ItemStack getBestTool(BlockState state) {
		ItemStack stack = bestTools.get(state);
		
		if(stack != null) return stack;
		
		float score = Float.NEGATIVE_INFINITY;
		
		for(int i = 0; i < size; i++) {
			
			ItemStack other = stacks.get(i);
			
			float f = ItemUtil.getToolScore(other, state);
			
			if(f > score) {
				
				stack = other;
				score = f;
			}
		}
		
		bestTools.put(state, stack);
		
		return stack;
	}
	
	public ItemStack getThrowawayBlocks() {
		if(throwawayBlocks == null) {
			
			throwawayBlocks = getStackOf(Items.STONE);
		}
		
		return throwawayBlocks;
	}
	
	public ItemStack getWaterBucket() {
		if(waterBucket == null) {
			
			waterBucket = getStackOf(Items.WATER_BUCKET);
		}
		
		return waterBucket;
	}
	
	public boolean hasThrowawayBlocks() {
		return !getThrowawayBlocks().equals(ItemStack.EMPTY);
	}
	
	public boolean hasWaterBucket() {
		return !getWaterBucket().equals(ItemStack.EMPTY);
	}
	
	private ItemStack getStackOf(Item item) {
		for(int i = 0; i < size; i++) {
			
			ItemStack stack = stacks.get(i);
			
			if(stack.isOf(item)) return stack;
		}
		
		return ItemStack.EMPTY;
	}
	
	public DefaultedList<ItemStack> getStacks() {
		return stacks;
	}
	
	public int getSize() {
		return size;
	}
	
	public static ToolSet getTools() {
		PlayerInventory inv = PlayerContext.inventory();
		
		DefaultedList<ItemStack> stacks = inv.main;
		
		if(tools == null || !tools.getStacks().equals(stacks)) {
			
			tools = new ToolSet(stacks, 9);
		}
		
		return tools;
	}
	
}
