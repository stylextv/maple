package de.stylextv.lynx.gui;

import java.util.HashMap;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.util.ItemUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class ToolSet {
	
	private static ToolSet tools;
	
	private ItemStack[] stacks;
	
	private HashMap<BlockState, ItemStack> bestTools = new HashMap<>();
	
	
	
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
		return null;
	}
	
	public ItemStack getWaterBucket() {
		return null;
	}
	
	public boolean hasThrowawayBlocks() {
		return false;
	}
	
	public boolean hasWaterBucket() {
		return false;
	}
	
	private boolean hasItem(Item item) {
		for(ItemStack stack : stacks) {
			
			Item other = stack.getItem();
			
			if(other.equals(item)) return true;
		}
		
		return false;
	}
	
	public static void update() {
		PlayerInventory inv = PlayerContext.inventory();
		
		DefaultedList<ItemStack> main = inv.main;
		
		int l = 9;
		
		ItemStack[] stacks = new ItemStack[l];
		
		for(int i = 0; i < l; i++) {
			
			stacks[i] = main.get(i);
		}
		
		tools = new ToolSet(stacks);
	}
	
	public static ToolSet getTools() {
		if(tools == null) update();
		
		return tools;
	}
	
}
