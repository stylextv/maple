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
	
	private static final Item[] THROWAWAY_BLOCKS = new Item[] {
			Items.DIRT,
			Items.NETHERRACK,
			Items.COBBLESTONE,
			Items.STONE
	};
	
	private static ToolSet tools;
	
	private ItemStack[] stacks;
	
	private HashMap<BlockState, ItemStack> bestTools = new HashMap<>();
	
	private ItemStack throwawayBlocks;
	
	private ItemStack waterBucket;
	
	public ToolSet(ItemStack[] stacks) {
		this.stacks = stacks;
	}
	
	public ItemStack getBestTool(BlockState state) {
		ItemStack stack = bestTools.get(state);
		
		if(stack != null) return stack;
		
		float score = Float.NEGATIVE_INFINITY;
		
		for(ItemStack other : stacks) {
			
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
			
			for(Item item : THROWAWAY_BLOCKS) {
				
				ItemStack stack = getStackOf(item);
				
				throwawayBlocks = stack;
				
				if(!stack.equals(ItemStack.EMPTY)) break;
			}
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
		for(ItemStack stack : stacks) {
			
			if(stack.isOf(item)) return stack;
		}
		
		return ItemStack.EMPTY;
	}
	
	public ItemStack[] getStacks() {
		return stacks;
	}
	
	public static void updateTools() {
		PlayerInventory inv = PlayerContext.inventory();
		
		DefaultedList<ItemStack> list = inv.main;
		
		int l = 9;
		
		ItemStack[] stacks = new ItemStack[l];
		
		for(int i = 0; i < l; i++) {
			
			stacks[i] = list.get(i);
		}
		
		tools = new ToolSet(stacks);
	}
	
	public static ToolSet getTools() {
		if(tools == null) updateTools();
		
		return tools;
	}
	
}
